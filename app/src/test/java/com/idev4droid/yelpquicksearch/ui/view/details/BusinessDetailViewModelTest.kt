package com.idev4droid.yelpquicksearch.ui.view.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.idev4droid.yelpquicksearch.core.data.BusinessService
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.ui.view.details.viewmodel.BusinessDetailViewModel
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

class BusinessDetailViewModelTest {
    @Mock
    private lateinit var mockService: BusinessService
    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var businessDetailViewmodel: BusinessDetailViewModel

    @Mock
    lateinit var observer: Observer<Business?>

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        businessDetailViewmodel = BusinessDetailViewModel(mockService, schedulerProvider)
    }

    @Test
    fun `Test existing Business`() {
        // Given
        Mockito.`when`(mockService.fetchBusiness("test")).thenReturn(createTestObservable())
        businessDetailViewmodel.business.observeForever(observer)
        // When
        businessDetailViewmodel.fetchBusinessDetails("test")
        // Then
        assertThat(businessDetailViewmodel.business.value!!.id, `is`(createTestBusiness().id))
    }

    @Test
    fun `Test non existing Business`() {
        // Given
        Mockito.`when`(mockService.fetchBusiness("test")).thenReturn(null)
        businessDetailViewmodel.business.observeForever(observer)
        // When
        businessDetailViewmodel.fetchBusinessDetails("test")
        // Then
        assertNull(businessDetailViewmodel.business.value)
    }

    private fun createTestObservable(): Observable<Business>? {
        return Observable.just(createTestBusiness())
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