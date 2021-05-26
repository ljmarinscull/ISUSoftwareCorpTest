package com.project.acmetest.data.auth

import com.project.acmetest.data.model.SessionResponse
import com.project.acmetest.utils.Result

/**
 * Interface for authentication datasource.
 */
interface IAuthDataSource {

    /**
     * Login with username/password.
     * @param username
     * @param password
     * @return the authentication response.
     */
    fun loginUserAndPassword(username: String, password: String): Result<SessionResponse>
}