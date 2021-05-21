package com.project.acmetest.data.auth

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fake class for authentication.
 *
 * @constructor Empty constructor.
 */
class AuthFakeDataSourceProvider @Inject constructor() : IAuthDataSourceProvider {
    /**
     * Login with username/password.
     * @param username
     * @param password
     * @return true is username and password are correct or false otherwise.
     */
    override fun loginUserAndPassword(username: String, password: String) : Boolean {
        if(username.isNotBlank() && password.isNotBlank()){
            if (username == "admin" && password == "admin123")
                return true
        }
        return false
    }
}