package com.mafia.chores.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mafia.chores.*
import com.mafia.chores.backend.ApiServiceImp
import com.mafia.chores.backend.RetrofitBuilder
import com.mafia.chores.data.User
import com.mafia.chores.intent.RegisterLoginUserIntent
import com.mafia.chores.states.RegisterLoginUserState
import com.mafia.chores.viewmodels.RegisterLoginUserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.register_login_activity.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class RegisterLoginActivity: AppCompatActivity() {
    private lateinit var addUserViewModel: RegisterLoginUserViewModel
    private val user = User()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_login_activity)
        setupViewModel(user)
        setupButtons()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }


    private fun setupButtons() {
        registerButton.setOnClickListener {
            lifecycleScope.launch {
                val registerUser = User()
                registerUser.username = emailText.text.toString()
                registerUser.password = passwordText.text.toString()
                addUserViewModel = RegisterLoginUserViewModel(registerUser, MainRepository(ApiServiceImp(RetrofitBuilder.apiService)) )
                addUserViewModel.userIntent.send(RegisterLoginUserIntent.CreateUser)
            }
        }

        loginButton.setOnClickListener {
            lifecycleScope.launch {
                val loginUser = User()
                loginUser.username = emailText.text.toString()
                loginUser.password = passwordText.text.toString()
                addUserViewModel = RegisterLoginUserViewModel(loginUser, MainRepository(ApiServiceImp(RetrofitBuilder.apiService)))
                addUserViewModel.userIntent.send(RegisterLoginUserIntent.LoginUser)
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            addUserViewModel.state.collect {
                when (it) {
                    is RegisterLoginUserState.Idle -> {
                        Log.d(this@RegisterLoginActivity.javaClass.simpleName, "##### IDLE STATE")
                    }
                    is RegisterLoginUserState.Error -> {
                        Toast.makeText(this@RegisterLoginActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                    is RegisterLoginUserState.CreateUser -> {
                        Toast.makeText(this@RegisterLoginActivity, "ASDASDASDASD", Toast.LENGTH_LONG).show()
                    }
                    is RegisterLoginUserState.LoginUser -> {
                        finish()
                    }
                }
            }
        }
    }

  private fun setupViewModel(user: User) {
      addUserViewModel = ViewModelProvider(
          this, AddUserViewModelFactory(user,
              ApiServiceImp(
                  RetrofitBuilder.apiService)
          )
      ).get(RegisterLoginUserViewModel::class.java)
  }
}