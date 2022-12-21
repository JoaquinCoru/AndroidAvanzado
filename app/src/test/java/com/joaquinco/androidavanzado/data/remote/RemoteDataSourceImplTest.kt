package com.joaquinco.androidavanzado.data.remote

import com.google.common.truth.Truth
import com.joaquinco.androidavanzado.base.BaseNetworkTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoteDataSourceImplTest: BaseNetworkTest(){

    // SUT o UUT
    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @Before
    fun setUp(){
        remoteDataSourceImpl = RemoteDataSourceImpl(api)
    }

    @Test
    fun `WHEN getHeros EXPECT success return hero list`() = runTest{
        // GIVEN


        // WHEN
        val actual = remoteDataSourceImpl.getHeros()

        // THEN
        Truth.assertThat(actual).hasSize(15)
        Truth.assertThat(actual[0].name).isEqualTo("Maestro Roshi")
    }

    @Test
    fun `WHEN getLocations EXPECTS sucess return locations list`() = runTest{
        // GIVEN


        // WHEN
        val actual = remoteDataSourceImpl.getLocations("ID")

        // THEN
        Truth.assertThat(actual).hasSize(1)
        Truth.assertThat(actual[0].latitud).isEqualTo("35.71867899343361")
    }

}