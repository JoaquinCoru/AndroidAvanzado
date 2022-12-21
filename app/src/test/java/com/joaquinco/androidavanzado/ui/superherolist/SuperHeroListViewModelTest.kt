package com.joaquinco.androidavanzado.ui.superherolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.joaquinco.androidavanzado.domain.Repository
import com.joaquinco.androidavanzado.utils.generateHeros
import com.joaquinco.androidavanzado.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SuperHeroListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // SUT o UUT
    private lateinit var superHeroListViewModel: SuperHeroListViewModel

    // Dependencies
    private lateinit var repository: Repository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp(){
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `WHEN getSuperheros EXPECTS success returns list of superheros`() = runTest {
        // GIVEN
        repository = mockk()
        superHeroListViewModel = SuperHeroListViewModel(repository)
        coEvery {  repository.getHerosWithCache() } returns generateHeros()

        // WHEN
        val actual = superHeroListViewModel.getSuperheros()
        val actualLiveData = superHeroListViewModel.heros.getOrAwaitValue()

        // THEN
        Truth.assertThat(actualLiveData).containsExactlyElementsIn(generateHeros())
    }


        @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}