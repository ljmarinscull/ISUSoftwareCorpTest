package com.project.acmetest.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.acmetest.R
import com.project.acmetest.data.auth.IAuthRepository
import com.project.acmetest.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * AuthViewModel
 *
 * ViewModel that handles authentication logic.
 *
 * @property repository Repository to perform authentication
 * @property _loginForm MutableLiveData to store the state of the process of authentication.
 * @property loginFormState LiveData to observe the state of the process of authentication.
 * @property _loginResult MutableLiveData to store the result of the authentication.
 * @property loginResult LiveData to observe the result of the authentication.
 *
 * @constructor Empty constructor.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: IAuthRepository
    ) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun loginUserAndPassword(username: String, password: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.loginUserAndPassword(username, password)
            }
            if (result is Result.Success) {
                _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.name))
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank() && username.length >= 4
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank() && password.length >= 8
    }
}