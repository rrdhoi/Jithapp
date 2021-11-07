package com.pajokka.githubuser.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pajokka.githubuser.R
import com.pajokka.githubuser.databinding.HomeFragmentBinding
import com.pajokka.githubuser.ui.detail.DetailActivity
import com.pajokka.githubuser.vo.Status
import com.pajokka.githubuser.utils.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {
    private lateinit var userAdapter: UserAdapter
    override val kodein: Kodein by kodein()
    private val factory: ViewModelFactory by instance()
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        isDarkMode()
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = UserAdapter {
            val userDetail = Intent(context, DetailActivity::class.java).putExtra(DetailActivity.DATA_USER, it)
            startActivity(userDetail)
        }
        setRecyclerview()
        searchUser()
    }

    private fun searchUser() {
        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    isLoading(true)
                    isUserNotFound(false)
                    getDataUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDataUser(user: String) {
        homeViewModel.getUser(user).observe(viewLifecycleOwner) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        isLoading(false)

                        if (it.data != null) {
                            isUserNotFound(false)
                            userAdapter.setDataUser(it.data)
                            userAdapter.notifyDataSetChanged()

                            if (it.data.isEmpty()) {
                                isUserNotFound(true)
                            }
                        }
                    }
                    Status.LOADING -> {
                        isLoading(true)
                        isUserNotFound(false)
                    }
                    Status.ERROR -> {
                        isLoading(false)
                        isUserNotFound(true)
                    }
                }
            }
        }
    }

    private fun setRecyclerview() {
        with(binding.rvUsers) {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun isLoading(state: Boolean) {
        when (state) {
            true -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvUsers.visibility = View.GONE

            }
            false -> {
                binding.progressBar.visibility = View.GONE
                binding.rvUsers.visibility = View.VISIBLE
            }
        }
    }

    private fun isUserNotFound(state: Boolean) {
        when (state) {
            true -> {
                binding.avUser.setAnimation(R.raw.user_not_found)
                binding.tvUser.text = resources.getString(R.string.user_not_found)
                binding.avUser.visibility = View.VISIBLE
                binding.tvUser.visibility = View.VISIBLE
                binding.rvUsers.visibility = View.GONE
            }
            false -> {
                binding.avUser.visibility = View.GONE
                binding.tvUser.visibility = View.GONE
            }
        }
    }

    private fun isDarkMode() {
        homeViewModel.getThemeSettings().observe(viewLifecycleOwner, { isDarkModeActive ->
            if (isDarkModeActive) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}