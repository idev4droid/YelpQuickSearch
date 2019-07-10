package com.idev4droid.yelpquicksearch.ui.view.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.idev4droid.yelpquicksearch.core.data.BusinessService
import com.idev4droid.yelpquicksearch.core.data.model.Business
import com.idev4droid.yelpquicksearch.ui.view.details.viewmodel.BusinessDetailViewModel
import com.idev4droid.yelpquicksearch.utils.SchedulerProvider
import com.idev4droid.yelpquicksearch.utils.createTestBusiness
import io.reactivex.Maybe
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
        val createdTestBusiness = createTestBusiness()
        Mockito.`when`(mockService.fetchBusiness("test")).thenReturn(createTestObservable(createdTestBusiness))
        businessDetailViewmodel.business.observeForever(observer)
        // When
        businessDetailViewmodel.fetchBusinessDetails("test")
        // Then
        assertThat(businessDetailViewmodel.business.value!!.id, `is`(createdTestBusiness.id))

        assertThat(businessDetailViewmodel.getName(), `is`(createdTestBusiness.name))
        assertThat(businessDetailViewmodel.getPrice(), `is`(createdTestBusiness.price))
        assertThat(businessDetailViewmodel.getPictures().size, `is`(createdTestBusiness.photos?.size))
        assertThat(businessDetailViewmodel.getWebsite(), `is`(createdTestBusiness.url))
        assertThat(businessDetailViewmodel.getDisplayAddress(), `is`("24 test road, test city, test country, 12345"))
        assertThat(businessDetailViewmodel.getCategories(), `is`("Delis, Sandwiches"))
        assertThat(businessDetailViewmodel.getPhone(), `is`(createdTestBusiness.phone))

        assertThat(businessDetailViewmodel.business.value!!.isClosed, `is`(createdTestBusiness.isClosed))
        assertThat(businessDetailViewmodel.business.value!!.imageUrl, `is`(createdTestBusiness.imageUrl))
        assertThat(businessDetailViewmodel.business.value!!.reviewCount, `is`(createdTestBusiness.reviewCount))
        assertThat(businessDetailViewmodel.business.value!!.rating, `is`(createdTestBusiness.rating))
        assertThat(businessDetailViewmodel.business.value!!.distance, `is`(createdTestBusiness.distance))
        assertThat(businessDetailViewmodel.business.value!!.displayPhone, `is`(createdTestBusiness.displayPhone))
    }

    @Test
    fun `Test non existing Business`() {
        // Given
        Mockito.`when`(mockService.fetchBusiness("test")).thenReturn(Maybe.empty())
        businessDetailViewmodel.business.observeForever(observer)
        // When
        businessDetailViewmodel.fetchBusinessDetails("test")
        // Then
        assertNull(businessDetailViewmodel.business.value)
    }

    private fun createTestObservable(createdTestBusiness: Business): Maybe<Business>? {
        return Maybe.just(createdTestBusiness)
    }
}