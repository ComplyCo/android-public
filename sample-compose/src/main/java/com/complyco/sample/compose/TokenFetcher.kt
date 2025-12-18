package com.complyco.sample.compose

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.ivangarzab.bark.Bark
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

data class JwtServerResponse(
    @SerializedName("expires_in") var expiresIn: Int? = null,
    @SerializedName("token") var token: String? = null,
    @SerializedName("token_type") var tokenType: String? = null
)

/**
 * Fetches JWT tokens from a server endpoint and manages periodic refresh.
 *
 * @param url The URL endpoint to fetch JWT tokens from
 * @param defaultRefreshIntervalMillis Default interval between token refreshes if expiresIn is not provided
 */
class TokenFetcher(
    private val url: String,
    private val defaultRefreshIntervalMillis: Long = TimeUnit.MINUTES.toMillis(5)
) {
    private val _lastToken = MutableStateFlow("")

    private val client = OkHttpClient()
    private val gson = Gson()

    private var fetchJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * Starts periodic token fetching.
     * Fetches immediately and then schedules subsequent fetches based on the token's expiresIn value.
     */
    fun start() {
        if (fetchJob?.isActive == true) {
            Bark.w("TokenFetcher already started")
            return
        }

        fetchJob = scope.launch {
            while (isActive) {
                val nextDelayMillis = fetchToken()
                if (nextDelayMillis > 0) {
                    delay(nextDelayMillis)
                } else {
                    // If fetch failed, retry with default interval
                    delay(defaultRefreshIntervalMillis)
                }
            }
        }

        Bark.d("TokenFetcher started for URL: $url")
    }

    /**
     * Stops the periodic token fetching.
     */
    fun stop() {
        fetchJob?.cancel()
        fetchJob = null
        Bark.d("TokenFetcher stopped")
    }

    /**
     * Fetches a token from the server and returns the delay until the next fetch.
     *
     * @return The delay in milliseconds until the next fetch should occur
     */
    private suspend fun fetchToken(): Long {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(url)
                    .build()

                val response: Response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    Bark.e("Failed to fetch token: HTTP ${response.code}")
                    return@withContext defaultRefreshIntervalMillis
                }

                val body = response.body?.string()
                if (body.isNullOrEmpty()) {
                    Bark.e("Empty response body from token endpoint")
                    return@withContext defaultRefreshIntervalMillis
                }

                val jwtResponse: JwtServerResponse = gson.fromJson(body, JwtServerResponse::class.java)

                val token = jwtResponse.token
                if (token.isNullOrEmpty()) {
                    Bark.e("Received null or empty token from server")
                    return@withContext defaultRefreshIntervalMillis
                }

                _lastToken.value = token
                Bark.d("Successfully fetched JWT token (expires in ${jwtResponse.expiresIn}s)")

                // Calculate next fetch delay based on expiresIn
                // Refresh at 80% of expiry time to ensure we always have a valid token
                val expiresInSeconds = jwtResponse.expiresIn ?: (defaultRefreshIntervalMillis / 1000).toInt()
                val refreshDelayMillis = (expiresInSeconds * 1000 * 0.8).toLong()

                refreshDelayMillis.coerceAtLeast(TimeUnit.SECONDS.toMillis(10))

            } catch (e: Exception) {
                Bark.e("Error fetching token: ${e.message}", e)
                defaultRefreshIntervalMillis
            }
        }
    }

    /**
     * Returns a lambda function that provides the current JWT token.
     * This lambda can be passed to Engine.initializeCompose as the jwtProducer argument.
     *
     * @return A lambda that returns the current JWT token string
     */
    fun getJwtProducer(): () -> String = {
        val token = _lastToken.value
        if (token.isEmpty()) {
            Bark.w("JWT token requested but none available yet")
        }
        token
    }

    /**
     * Performs an immediate fetch of the token without waiting for the periodic schedule.
     * Useful for initialization or manual refresh.
     *
     * @return True if the token was successfully fetched, false otherwise
     */
    suspend fun fetchNow(): Boolean {
        val delay = fetchToken()
        return delay != defaultRefreshIntervalMillis || _lastToken.value.isNotEmpty()
    }
}
