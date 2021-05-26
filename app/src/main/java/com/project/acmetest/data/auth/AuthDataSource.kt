package com.project.acmetest.data.auth

import com.project.acmetest.data.model.SessionResponse
import com.project.acmetest.utils.Result
import java.io.IOException
import javax.inject.Inject

/**
 * Class that handles authentication.
 *
 * @property source the source for the authentication.
 */
class AuthDataSource @Inject constructor(
    private val source: IAuthDataSourceProvider
    ): IAuthDataSource {

    override fun loginUserAndPassword(username: String, password: String): Result<SessionResponse> {
        try {
            val result = source.loginUserAndPassword(username,password)
            if (result)
                return Result.Success(SessionResponse())
            else
                return Result.Error(IOException("Invalid credentials."))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }
}