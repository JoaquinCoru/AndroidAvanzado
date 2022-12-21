package com.joaquinco.androidavanzado.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.joaquinco.androidavanzado.domain.Repository
import com.joaquinco.androidavanzado.utils.generateLocations
import com.joaquinco.androidavanzado.utils.generateSuperHeroDetail
import com.joaquinco.androidavanzado.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // SUT o UUT
    private lateinit var detailViewModel: DetailViewModel

    // Dependencies
    private lateinit var repository: Repository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mockk()
        detailViewModel = DetailViewModel(repository)
    }

    @Test
    fun `WHEN getDetail EXPECTS success then return SuperHeroDetail`(){
        // GIVEN
        coEvery { repository.getHeroDetail(any()) } returns DetailState.Success(
            generateSuperHeroDetail()
        )

        // WHEN
        detailViewModel.getHeroDetail("Name")
        val actualLiveData = detailViewModel.state.getOrAwaitValue()

        // THEN
        Truth.assertThat(actualLiveData).isEqualTo(DetailState.Success(generateSuperHeroDetail()))
    }

    @Test
    fun `WHEN getLocations EXPECTS success returns list of locations`() {
        // GIVEN
        coEvery { repository.getLocations(any()) } returns generateLocations()

        // WHEN
        detailViewModel.getHeroLocations("Id")
        val actualLiveData = detailViewModel.locations.getOrAwaitValue()

        // THEN
        Truth.assertThat(actualLiveData).containsExactlyElementsIn(generateLocations())
    }

/*
    @Test
    fun `WHEN setLike EXPECTS success then return like state with success`() = runTest{
        // GIVEN
        coEvery { repository.getHeroDetail(any()) } returns DetailState.Success(
            generateSuperHeroDetail()
        )
        coEvery { repository.setLike(any()) } returns LikeState.Success()

        // WHEN
        detailViewModel.getHeroDetail("name")
        detailViewModel.setLike()
        val actualLiveData = detailViewModel.likeState.getOrAwaitValue()

        // THEN
        Truth.assertThat(actualLiveData).isEqualTo(LikeState.Success())

    }
*/

        @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}