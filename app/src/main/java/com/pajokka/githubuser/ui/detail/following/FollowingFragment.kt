package com.pajokka.githubuser.ui.detail.following

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pajokka.githubuser.databinding.FollowingFragmentBinding
import com.pajokka.githubuser.ui.detail.DetailActivity
import com.pajokka.githubuser.ui.detail.FollowAdapter
import com.pajokka.githubuser.utils.Preferences
import com.pajokka.githubuser.utils.ViewModelFactory
import com.pajokka.githubuser.vo.Status
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class FollowingFragment : Fragment(), KodeinAware {
    private lateinit var binding: FollowingFragmentBinding
    private lateinit var followAdapter: FollowAdapter
    private lateinit var viewModel: FollowingViewModel
    override val kodein: Kodein by kodein()
    private val factory: ViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FollowingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(FollowingViewModel::class.java)

        followAdapter = FollowAdapter {
            val userDetail =
                Intent(context, DetailActivity::class.java).putExtra(DetailActivity.DATA_USER, it)
            startActivity(userDetail)
        }
        setRecyclerview()

        isLoading(true)

        val usernamePref = Preferences(requireContext())
        val usernameUser = usernamePref.getValues(DetailActivity.USERNAME_USER)

        getDataUser(usernameUser.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDataUser(user: String) {
        viewModel.getFollowingUser(user).observe(viewLifecycleOwner) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        isLoading(false)

                        if (it.data != null) {
                            isUserNotFound(false)
                            followAdapter.setDataUser(it.data)
                            followAdapter.notifyDataSetChanged()
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
            adapter = followAdapter
        }
    }

    private fun isLoading(state: Boolean) {
        when (state) {
            true -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvUsers.visibility = View.GONE
                isUserNotFound(false)
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
}