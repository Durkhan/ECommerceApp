package com.tasks.ecommerceapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.Results
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.domain.usecases.login.LoginCustomerUseCase
import com.tasks.ecommerceapp.presentation.signin.SignInViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class SignViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var dataStoreManager: DataStoreManager


    private var loginCustomerUseCase: LoginCustomerUseCase= mockk(relaxed = true)

    private lateinit var viewModel: SignInViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = SignInViewModel(loginCustomerUseCase, dataStoreManager)
    }


    @Test
    fun signIn_success() = runTest {
        val email = "test@gmail.com"
        val password = "password"

        val expectedResponse = CustomerLoginResponse(true, "token")
        coEvery { loginCustomerUseCase(email, password) } returns Results.Success(expectedResponse)
        viewModel.signIn(email, password)
        val result = viewModel.signInResult.getOrAwaitValueTest()
        assertEquals(result,(Results.Success(expectedResponse)))
    }


}