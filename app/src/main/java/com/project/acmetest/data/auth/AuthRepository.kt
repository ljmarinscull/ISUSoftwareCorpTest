package com.project.acmetest.data.auth

import javax.inject.Inject

/**
 * Class that requests authentication.
 *
 * @property dataSource the datasource for the authentication.
 *
 * @constructor Creates an AuthRepository.
 */
class AuthRepository @Inject constructor(
    private val dataSource: IAuthDataSource
    ): IAuthRepository {

    override fun loginUserAndPassword(username: String, password: String)
        = dataSource.loginUserAndPassword(username, password)
}