package com.idev4droid.yelpquicksearch.ui.view.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.idev4droid.yelpquicksearch.core.data.BusinessService
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.core.data.model.BusinessFilter
import com.idev4droid.yelpquicksearch.ui.view.list.viewmodel.BusinessListViewModel
import com.idev4droid.yelpquicksearch.utils.SchedulerProvider
import com.idev4droid.yelpquicksearch.utils.createTestBusinessesObservable
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
        Mockito.`when`(mockService.fetchBusinesses(null, 37.786882, -122.399972, 20, 0))
            .thenReturn(createTestBusinessesObservable(1))
        businessListViewModel.businesses.observeForever(observer)
        // When
        businessListViewModel.loadBusinesses()
        // Then
        assertThat(businessListViewModel.businesses.value!!.size, `is`(1))
    }

    @Test
    fun `Test 15 business`() {
        // Given
        Mockito.`when`(mockService.fetchBusinesses(null, 37.786882, -122.399972, 20, 0))
            .thenReturn(createTestBusinessesObservable(15))
        businessListViewModel.businesses.observeForever(observer)
        // When
        businessListViewModel.loadBusinesses()
        // Then
        assertThat(businessListViewModel.businesses.value!!.size, `is`(15))
    }

    @Test
    fun `Test 1 business with filtering`() {
        // Given
        Mockito.`when`(mockService.fetchBusinesses("delis", 37.786882, -122.399972, 20, 0))
            .thenReturn(createTestBusinessesObservable(1))
        businessListViewModel.businesses.observeForever(observer)
        // When
        businessListViewModel.filter(BusinessFilter("delis", null, "delis"))
        // Then
        assertThat(businessListViewModel.businesses.value!!.size, `is`(1))
    }

    @Test
    fun `Test businesses with paging`() {
        // Given
        Mockito.`when`(mockService.fetchBusinesses(null, 37.786882, -122.399972, 20, 0))
            .thenReturn(createTestBusinessesObservable(20))
        Mockito.`when`(mockService.fetchBusinesses(null, 37.786882, -122.399972, 20, 20))
            .thenReturn(createTestBusinessesObservable(20))
        businessListViewModel.businesses.observeForever(observer)
        // When
        businessListViewModel.loadBusinesses()
        businessListViewModel.loadNextPage()
        // Then
        assertThat(businessListViewModel.businesses.value!!.size, `is`(40))
    }

    @Test
    fun `Test null business`() {
        // Given
        Mockito.`when`(mockService.fetchBusinesses(null, 37.786882, -122.399972, 20, 0)).thenReturn(null)
        businessListViewModel.businesses.observeForever(observer)
        // When
        businessListViewModel.loadBusinesses()
        // Then
        assertNull(businessListViewModel.businesses.value)
    }
}
