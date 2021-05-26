package com.project.acmetest.data.auth

import com.project.acmetest.data.model.SessionResponse
import com.project.acmetest.utils.Result

/**
 * Interface for authentication repository.
 */
interface IAuthRepository {

    /**
     * Login with username/password.
     * @param username the username of the User.
     * @param password the password of the User.
     * @return the authentication response.
     */
    fun loginUserAndPassword(username: String, password: String): Result<SessionResponse>
}