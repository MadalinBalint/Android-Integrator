package com.mendelin.androidintegrator.tmdb.data.api

import com.denisbrandi.netmock.*
import com.denisbrandi.netmock.engine.NetMockEngine
import com.denisbrandi.netmock.resources.readFromResources
import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.tmdb.component.BuildConfig
import com.mendelin.androidintegrator.tmdb.data.api.DefaultTmdbApi.Companion.BASE_URL
import com.mendelin.androidintegrator.tmdb.data.api.DefaultTmdbApi.Companion.MOVIE_GENRES_URL
import com.mendelin.androidintegrator.tmdb.data.api.DefaultTmdbApi.Companion.MOVIE_NOW_PLAYING_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.*
import org.junit.Assert.assertTrue

class DefaultTmdbApiTest {
    private lateinit var config: MockEngineConfig
    private lateinit var netMock: NetMockEngine
    private lateinit var client: HttpClient
    private lateinit var sut: DefaultTmdbApi

    @Before
    fun setUp() {
        config = MockEngineConfig()
        netMock = NetMockEngine(config)
        client = HttpClient(netMock) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 3000L
            }

            defaultRequest {
                headers {
                    header("Accept", "application/json")
                    header("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                }
            }
        }
        sut = DefaultTmdbApi(client)
    }

    @Test
    fun `EXPECT response WHEN getMovieGenres is successful`() = runTest {
        netMock.addMock(EXPECTED_MOVIE_GENRES_REQUEST, EXPECTED_MOVIE_GENRES_RESPONSE)

        val result = sut.getMovieGenres()

        assertTrue(result is AiResult.Success)
    }

    @Test
    fun `EXPECT error WHEN getMovieGenres fails`() = runTest {
        netMock.addMock(EXPECTED_MOVIE_GENRES_REQUEST, NetMockResponse(code = 500))

        val result = sut.getMovieGenres()

        assertTrue(result is AiResult.Failure)
    }

    @Test
    fun `EXPECT response WHEN getMoviesNowPlaying is successful`() = runTest {
        netMock.addMock(EXPECTED_MOVIE_NOW_PLAYING_REQUEST, EXPECTED_MOVIE_NOW_PLAYING_RESPONSE)

        val result = sut.getMoviesNowPlaying(PAGE)

        assertTrue(result is AiResult.Success)
    }

    @Test
    fun `EXPECT error WHEN getMoviesNowPlaying fails`() = runTest {
        netMock.addMock(EXPECTED_MOVIE_NOW_PLAYING_REQUEST, NetMockResponse(code = 500))

        val result = sut.getMoviesNowPlaying(PAGE)

        assertTrue(result is AiResult.Failure)
    }

    private companion object {
        const val PAGE = 1

        val HEADERS = mapOf(
            "Accept" to "application/json",
            "Authorization" to "Bearer ${BuildConfig.TMDB_API_KEY}"
        )

        val RESPONSE_HEADERS = mapOf(
            "Content-Type" to "application/json"
        )

        val EXPECTED_MOVIE_GENRES_REQUEST = NetMockRequest(
            method = Method.Get,
            requestUrl = "$BASE_URL$MOVIE_GENRES_URL",
            mandatoryHeaders = HEADERS,
        )

        val EXPECTED_MOVIE_GENRES_RESPONSE = NetMockResponse(
            code = 200,
            mandatoryHeaders = RESPONSE_HEADERS,
            body = readFromResources("response/get_genres_response.json")
        )

        val EXPECTED_MOVIE_NOW_PLAYING_REQUEST = NetMockRequest(
            method = Method.Get,
            requestUrl = "$BASE_URL$MOVIE_NOW_PLAYING_URL?page=$PAGE",
            mandatoryHeaders = HEADERS,
        )

        val EXPECTED_MOVIE_NOW_PLAYING_RESPONSE = NetMockResponse(
            code = 200,
            mandatoryHeaders = RESPONSE_HEADERS,
            body = readFromResources("response/get_movies_now_playing_response.json")
        )
    }
}
