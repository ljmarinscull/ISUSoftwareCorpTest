package com.project.acmetest.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.project.acmetest.R
import com.project.acmetest.databinding.ActivityLoginBinding
import com.project.acmetest.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * LoginActivity
 *
 * @property binding The object that references (immutably) the view and contains the objects that are displayed in the view.
 * @property viewModel The ViewModel that handles the authentication logic.
 *
 * @constructor Empty constructor.
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = getString(R.string.title_activity_login)

        initObservers()
    }

    /**
     * Start observing viewmodel livedata
     */
    private fun initObservers() = with(binding) {

        viewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            bSignIn.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        viewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                showToast(loginResult.error)
            }
            if (loginResult.success != null) {
                showToast(R.string.welcome)
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                finish()
            }
        })

        username.afterTextChanged {
            viewModel.loginDataChanged(
                username.text.trim().toString(),
                password.text.trim().toString()
            )
        }

        password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.loginUserAndPassword(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            bSignIn.setOnClickListener {
                viewModel.loginUserAndPassword(username.text.toString(), password.text.toString())
            }
        }
    }

    /**
     * Show Toast message.
     */
    private fun showToast(@StringRes errorString: Int) {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}


