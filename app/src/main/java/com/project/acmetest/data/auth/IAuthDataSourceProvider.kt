package com.project.acmetest.data.auth

/**
 * Interface for authentication data source provider.
 */
interface IAuthDataSourceProvider {
    /**
     * Login with username/password.
     * @param username the username of the User.
     * @param password the password of the User.
     * @return true if the authentication is satisfactory and false otherwise.
     */
    fun loginUserAndPassword(username: String, password: String) : Boolean
}