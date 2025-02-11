package com.mendelin.androidintegrator.tmdb.presentation.viewmodel

import androidx.paging.*
import androidx.paging.testing.asSnapshot
import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.testing.*
import com.mendelin.androidintegrator.tmdb.domain.model.*
import com.mendelin.androidintegrator.tmdb.domain.usecase.*
import com.mendelin.androidintegrator.tmdb.presentation.paging.TmdbMoviesPagingSource
import com.mendelin.androidintegrator.tmdb.presentation.viewmodel.TmdbViewModel.TmdbState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.kotlin.*


@OptIn(ExperimentalCoroutinesApi::class)
class TmdbViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getMovieGenres: GetMovieGenres = mock()

    private val getMoviesNowPlaying: GetMoviesNowPlaying = mock()

    private lateinit var viewModel: TmdbViewModel
    private lateinit var stateEventObserver: FlowTestObserver<TmdbState>
    private lateinit var genresObserver: FlowTestObserver<List<Genre>>
    private lateinit var pagingObserver: FlowTestObserver<PagingData<Movie>>

    @Before
    fun setup() {
        viewModel = TmdbViewModel(getMovieGenres, getMoviesNowPlaying)
        stateEventObserver = viewModel.state.test()
        genresObserver = viewModel.genres.state.test()
        pagingObserver = viewModel.movies.state.test()
    }

    @Test
    fun `EXPECT list of genres WHEN getGenres is successful`() = runTest {
        whenever(getMovieGenres()).thenReturn(AiResult.Success(GENRE_LIST))
        viewModel.getGenres()

        advanceUntilIdle()

        assertEquals(GENRE_LIST, genresObserver.value())
    }

    @Test
    fun `EXPECT empty list WHEN getGenres fails`() = runTest {
        whenever(getMovieGenres()).thenReturn(AiResult.Failure("Error"))
        viewModel.getGenres()

        advanceUntilIdle()

        assertEquals(emptyList<Genre>(), genresObserver.value())
    }

    @Test
    fun `EXPECT Idle WHEN observing initial state`() = runTest {
        assertEquals(TmdbState.Idle, stateEventObserver.value())
    }

    @Test
    fun `EXPECT Loading WHEN initial state is changed to Loading`() = runTest {
        viewModel.updateState(TmdbState.Loading)
        advanceUntilIdle()
        assertEquals(TmdbState.Loading, stateEventObserver.value())
    }

    @Test
    fun `EXPECT Refresh WHEN initial state is changed to Refresh`() = runTest {
        viewModel.updateState(TmdbState.Refresh)
        advanceUntilIdle()
        assertEquals(TmdbState.Refresh, stateEventObserver.value())
    }

    @Test
    fun `EXPECT Error WHEN initial state is changed to Error`() = runTest {
        viewModel.updateState(TmdbState.Error("Error"))

        advanceUntilIdle()

        assertEquals(TmdbState.Error("Error"), stateEventObserver.value())
    }

    @Test
    fun `EXPECT movie list WHEN getMovies is successful`() = runTest {
        whenever(getMoviesNowPlaying(any())).thenReturn(AiResult.Success(NOW_PLAYING))
        viewModel.getMovies()

        advanceUntilIdle()

        val actual = pagingObserver.getFlow().asSnapshot().take(TmdbViewModel.TMDB_PAGE_SIZE)

        assertEquals(MOVIE_LIST, actual)
        stateEventObserver.assertValueHistory(
            listOf(
                TmdbState.Idle,
                TmdbState.Loading,
                TmdbState.Idle
            )
        )
    }

    @Test
    fun `EXPECT error WHEN getMoviesNowPlaying fails`() = runTest {
        whenever(getMoviesNowPlaying(any())).thenReturn(AiResult.Failure("Failure"))
        val pagingSource = TmdbMoviesPagingSource(getMoviesNowPlaying)

        val params = PagingSource.LoadParams.Refresh<Int>(
            key = null,
            loadSize = TmdbViewModel.TMDB_PAGE_SIZE,
            placeholdersEnabled = true
        )
        val result = pagingSource.load(params)

        advanceUntilIdle()

        assertTrue(result is PagingSource.LoadResult.Error)
        val errorResult = result as PagingSource.LoadResult.Error
        assertEquals("Failure", errorResult.throwable.message)
    }

    @Test
    fun `EXPECT correct refresh key WHEN getRefreshKey is successful`() = runTest {
        val page1 = PagingSource.LoadResult.Page(
            data = MOVIE_LIST,
            prevKey = null,
            nextKey = 2
        )
        val page2 = PagingSource.LoadResult.Page(
            data = MOVIE_LIST,
            prevKey = 1,
            nextKey = 3
        )
        val page3 = PagingSource.LoadResult.Page(
            data = MOVIE_LIST,
            prevKey = 2,
            nextKey = null
        )

        val pagingState = PagingState(
            pages = listOf(page1, page2, page3),
            anchorPosition = TmdbViewModel.TMDB_PAGE_SIZE+4, // This index should lie inside page2's range (indexes TMDB_PAGE_SIZE+1..TMDB_PAGE_SIZE*2).
            config = PagingConfig(pageSize = TmdbViewModel.TMDB_PAGE_SIZE, enablePlaceholders = false),
            leadingPlaceholderCount = 0
        )

        // Instantiate the PagingSource and invoke getRefreshKey.
        val pagingSource = TmdbMoviesPagingSource(getMoviesNowPlaying)
        val refreshKey = pagingSource.getRefreshKey(pagingState)

        val expectedRefreshKey = 2

        assertEquals(expectedRefreshKey, refreshKey)
    }

    private companion object {
        val GENRE_LIST = List(10) {
            Genre(id = it, name = "Genre $it")
        }

        val MOVIE_LIST = List(TmdbViewModel.TMDB_PAGE_SIZE) {
            Movie(
                backdropPath = if (it % 2 == 0) "https://image.tmdb.org/t/p/original/backdrop_$it.jpg" else "",
                genreIds = listOf(it),
                id = it,
                originalLanguage = "en",
                originalTitle = "Original title $it",
                overview = "Overview no. $it",
                posterPath = if (it % 2 == 0) "https://image.tmdb.org/t/p/original/poster_$it.jpg" else "",
                releaseDate = "01/01/2025",
                title = "Title $it"
            )
        }

        val NOW_PLAYING = NowPlaying(
            page = 1,
            totalPages = 2,
            totalResults = TmdbViewModel.TMDB_PAGE_SIZE,
            results = MOVIE_LIST
        )
    }
}
