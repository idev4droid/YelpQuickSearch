package com.idev4droid.yelpquicksearch.ui.view.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.idev4droid.yelpquicksearch.core.data.BusinessResponse
import com.idev4droid.yelpquicksearch.core.data.BusinessService
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.ui.view.list.viewmodel.BusinessListViewModel
import com.idev4droid.yelpquicksearch.utils.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertNull
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class BusinessListViewModelTest {
    @Mock
    private lateinit var mockService: BusinessService
    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var businessListViewModel: BusinessListViewModel

    @Mock
    lateinit var observer: Observer<MutableList<Business>>

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        businessListViewModel = BusinessListViewModel(mockService, schedulerProvider)
    }

    @Test
    fun `Test 1 business`() {
        // Given
        Mockito.`when`(mockService.fetchBusinesses(null, 37.786882, -122.399972)).thenReturn(createTestObservable())
        businessListViewModel.businesses.observeForever(observer)
        // When
        businessListViewModel.loadBusinesses()
        // Then
        assertThat(businessListViewModel.businesses.value!!.size, `is`(1))
    }

    @Test
    fun `Test null business`() {
        // Given
        Mockito.`when`(mockService.fetchBusinesses(null, 37.786882, -122.399972)).thenReturn(null)
        businessListViewModel.businesses.observeForever(observer)
        // When
        businessListViewModel.loadBusinesses()
        // Then
        assertNull(businessListViewModel.businesses.value)
    }

    private fun createTestObservable(): Observable<BusinessResponse>? {
        return Observable.just(createTestBusinessResponse())
    }

    private fun createTestBusinessResponse(): BusinessResponse {
        val businessResponse = BusinessResponse()
        businessResponse.businesses = listOf(createTestBusiness())
        return businessResponse
    }

    private fun createTestBusiness(): Business {
        return Business(
            "test",
            "test",
            "test",
            false,
            "",
            0,
            5.0,
            "",
            arrayOf(),
            mapOf(),
            0.0,
            "",
            "",
            listOf(),
            listOf()
        )
    }
}
