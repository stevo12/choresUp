package com.mafia.chores.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mafia.chores.viewmodels.MainViewModel
import com.mafia.chores.R
import com.mafia.chores.ViewModelFactory
import com.mafia.chores.adapters.MainAdapter
import com.mafia.chores.backend.ApiServiceImp
import com.mafia.chores.backend.RetrofitBuilder
import com.mafia.chores.data.User
import com.mafia.chores.intent.MainIntent
import com.mafia.chores.states.MainState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf(), this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClick()
    }

    private fun setupClick(){
        fetchUsers.setOnClickListener {
            lifecycleScope.launch{
                mainViewModel.userIntent.send(MainIntent.FetchUsers)
            }
        }

        goToLoginRegister.setOnClickListener {
            val intent = Intent(this,RegisterLoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupUI() {
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.run {
            addItemDecoration(DividerItemDecoration(
                usersRecyclerView.context,
                (usersRecyclerView.layoutManager as LinearLayoutManager).orientation
            ))
        }
        usersRecyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this, ViewModelFactory(
                ApiServiceImp(
                    RetrofitBuilder.apiService))).get(MainViewModel::class.java)
    }

   private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {}
                    is MainState.Loading -> {
                        fetchUsers.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Users -> {
                        progressBar.visibility = View.GONE
                        fetchUsers.visibility = View.GONE
                        renderList(it.users)
                    }

                    is MainState.Error -> {
                        progressBar.visibility = View.GONE
                        fetchUsers.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        usersRecyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()

    }
}