package com.shopping.project

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shopping.project.data.DataRepository
import com.shopping.project.data.model.NewsDataArticleResponseItem
import com.shopping.project.module.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.KoinTest


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelUnitTest: KoinTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockkRule = MockKRule(this)

    lateinit var viewModel: MainViewModel

    @MockK
    lateinit var dataRepository: DataRepository

    @Before
    fun setUp() {
        viewModel = MainViewModel(
            dataRepository
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getStockData() success hit` () = runTest {
        /** Setup **/

        viewModel.getListStokBarang()
        coVerify(atLeast = 1) {
            dataRepository.getListStokBarang()
        }
    }

    @Test
    fun `test getStockData() check response data` () = runTest {
        /** Setup **/
        val mockResponse = mockk<List<NewsDataArticleResponseItem>>()

        val mockResponseSuccess = mockResponse

        coEvery {
            dataRepository.getListStokBarang()
        } returns mockResponseSuccess

        /** Act **/
        viewModel.getListStokBarang()

        /** Assert **/
        coVerify { dataRepository.getListStokBarang() }
        val param = viewModel.newsArticle.value
        Assert.assertEquals(mockResponseSuccess, param)
    }

    @Test
    fun `test getStockData() check response no data` () = runTest {
        /** Setup **/

        val mockResponseError = listOf<NewsDataArticleResponseItem>()


        coEvery {
            dataRepository.getListStokBarang()
        } returns mockResponseError

        /** Act **/
        viewModel.getListStokBarang()

        /** Assert **/
        coVerify { dataRepository.getListStokBarang() }
        val param = viewModel.newsArticle.value
        Assert.assertEquals(mockResponseError, param)
    }

}