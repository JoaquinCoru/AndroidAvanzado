package com.joaquinco.androidavanzado.data

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
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class RepositoryImplTest{

    private lateinit var repositoryImpl: RepositoryImpl
    private lateinit var localDataSource: LocalDataSource
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp(){
        localDataSource = mockk()
        remoteDataSource = mockk()

        repositoryImpl = RepositoryImpl(
            localDataSource,
            remoteDataSource,
            RemoteToPresentationMapper(),
            RemoteToLocalMapper(),
            LocalToPresentationMapper()
        )
    }

    @Test
    fun `WHEN login then Success return Success with token`() = runTest{
        // GIVEN
        coEvery { remoteDataSource.login() } returns Result.success("TOKEN")
        // WHEN
        val result = repositoryImpl.login()
        // THEN

        Truth.assertThat(result).isEqualTo(LoginState.Success("TOKEN"))
    }

    @Test
    fun `WHEN getHeros then Success return list from local and remote called`() = runTest{
        // GIVEN
        coEvery { localDataSource.getHeros() } returns emptyList()
        coEvery { localDataSource.insertHeros(any()) } returns Unit
        coEvery { remoteDataSource.getHeros() } returns generateHerosRemote()

        // WHEN
        repositoryImpl.getHerosWithCache()

        // THEN
        coVerify { remoteDataSource.getHeros() }
        coVerify(exactly = 2) { localDataSource.getHeros() }
    }

    @Test
    fun `WHEN getHeros then Success return list from local and remote called with FAKE`() = runTest{
        // GIVEN
        val localDataSource = FakeLocalDataSource()
        repositoryImpl = RepositoryImpl(
            localDataSource,
            remoteDataSource,
            RemoteToPresentationMapper(),
            RemoteToLocalMapper(),
            LocalToPresentationMapper()
        )

        coEvery { remoteDataSource.getHeros() } returns generateHerosRemote()

        // WHEN
        val actual = repositoryImpl.getHerosWithCache()

        // THEN
        Truth.assertThat(actual).isNotEmpty()
        Truth.assertThat(actual.first().name).isEqualTo("Name 0")
        coVerify { remoteDataSource.getHeros() }
        Truth.assertThat(actual).containsExactlyElementsIn(generateHeros())
    }

    @Test
    fun `WHEN getLocations THEN SUCCESS return not empty list with FAKE`() = runTest{
        // GIVEN
        val remoteDataSource = FakeRemoteDataSource()
        repositoryImpl = RepositoryImpl(
            localDataSource,
            remoteDataSource,
            RemoteToPresentationMapper(),
            RemoteToLocalMapper(),
            LocalToPresentationMapper()
        )

        // WHEN
        val actual = repositoryImpl.getLocations("ID")

        // THEN
        Truth.assertThat(actual).isNotEmpty()
        Truth.assertThat(actual.first().id).isEqualTo("ID: 0")
        Truth.assertThat(actual).containsExactlyElementsIn(generateLocations())
    }

    @Test
    fun `WHEN getHeroDetail then Success return Success with DetailHero`()= runTest{
        // GIVEN
        coEvery { remoteDataSource.getHeroDetail("Goku") } returns Result.success(
            generateSuperHeroRemote()
        )


        //WHEN
        val result = repositoryImpl.getHeroDetail("Goku")

        // THEN
        Truth.assertThat(result).isEqualTo(DetailState.Success(generateSuperHeroDetail()))
    }

    @Test
    fun `WHEN setLike then Success then return LikeState with Success`() = runTest {
        // GIVEN
        coEvery { remoteDataSource.setLike(any()) } returns Result.success(print("Exito"))
        coEvery { localDataSource.updateHero(any()) } returns Unit

        //WHEN
        val result = repositoryImpl.setLike(generateSuperHeroDetail())

        // THEN
        Truth.assertThat(result).isEqualTo(LikeState.Success())
    }
}