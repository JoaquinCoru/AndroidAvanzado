package com.joaquinco.androidavanzado.data

import android.util.Log
import com.google.common.truth.Truth
import com.joaquinco.androidavanzado.data.local.LocalDataSource
import com.joaquinco.androidavanzado.data.mappers.LocalToPresentationMapper
import com.joaquinco.androidavanzado.data.mappers.RemoteToLocalMapper
import com.joaquinco.androidavanzado.data.mappers.RemoteToPresentationMapper
import com.joaquinco.androidavanzado.data.remote.RemoteDataSource
import com.joaquinco.androidavanzado.fakes.FakeLocalDataSource
import com.joaquinco.androidavanzado.fakes.FakeRemoteDataSource
import com.joaquinco.androidavanzado.ui.detail.DetailState
import com.joaquinco.androidavanzado.ui.detail.LikeState
import com.joaquinco.androidavanzado.utils.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RepositoryImplWithFakesTest{

    private lateinit var repositoryImpl: RepositoryImpl
    private lateinit var localDataSource: LocalDataSource
    private val fakeremoteDataSource = FakeRemoteDataSource()

    @Before
    fun setUp(){
        localDataSource = mockk()

        repositoryImpl = RepositoryImpl(
            localDataSource,
            fakeremoteDataSource,
            RemoteToPresentationMapper(),
            RemoteToLocalMapper(),
            LocalToPresentationMapper()
        )
    }

    @Test
    fun `WHEN getHeroDetail EXPECT success and returns hero detail`() = runTest {
        // GIVEN

        // WHEN
        val actual = repositoryImpl.getHeroDetail("SUCCESS")

        // THEN
        assert(actual is DetailState.Success)
        Truth.assertThat((actual as DetailState.Success).hero.name).isEqualTo("Maestro Roshi")
    }

    @Test
    fun `WHEN getHeroDetail EXPECT failure with generic error`() = runTest {
        // GIVEN

        // WHEN
        val actual = repositoryImpl.getHeroDetail("NULL")

        // THEN
        assert(actual is DetailState.Failure)
        assert((actual as DetailState.Failure).error == "Null pointer error")
    }

    @Test
    fun `WHEN getHeroDetail EXPECT failure with generic error in success call`() = runTest {
        // GIVEN

        // WHEN
        val actual = repositoryImpl.getHeroDetail("SUCCESS_BUT_NULL")

        // THEN
        assert(actual is DetailState.Failure)
        assert((actual as DetailState.Failure).error == null)
    }

    @Test
    fun `WHEN getHeroDetail EXPECT failure with network error`() = runTest {
        // GIVEN

        // WHEN
        val actual = repositoryImpl.getHeroDetail("NETWORK_ERROR")

        // THEN
        assert(actual is DetailState.NetworkFailure)
        assert((actual as DetailState.NetworkFailure).code == 204)
    }


    @Test
    fun `WHEN getLocations THEN SUCCESS return not empty list with FAKE`() = runTest{
        // GIVEN

        // WHEN
        val actual = repositoryImpl.getLocations("ID")

        // THEN
        Truth.assertThat(actual).isNotEmpty()
        Truth.assertThat(actual.first().id).isEqualTo("ID: 0")
        Truth.assertThat(actual).containsExactlyElementsIn(generateLocations())
    }
}