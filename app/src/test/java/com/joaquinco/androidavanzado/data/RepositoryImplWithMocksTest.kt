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
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class RepositoryImplWithMocksTest{

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
    fun `WHEN login EXPECT failure with network error `() = runTest{
        // GIVEN
        coEvery { remoteDataSource.login() } returns  Result.failure(HttpException(Response.success(204,{})))
        // WHEN
        val result = repositoryImpl.login()
        // THEN
        assert(result is LoginState.NetworkFailure)
        assert(((result as LoginState.NetworkFailure).code == 204))
    }

    @Test
    fun `WHEN login EXPECT failure with generic error `() = runTest {
        // GIVEN
        coEvery { remoteDataSource.login() } returns Result.failure(Exception())
        // WHEN
        val result = repositoryImpl.login()
        // THEN
        assert(result is LoginState.Failure)
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