package com.pajokka.githubuser.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pajokka.githubuser.databinding.FavoriteFragmentBinding
import com.pajokka.githubuser.ui.detail.DetailActivity
import com.pajokka.githubuser.ui.home.UserAdapter
import com.pajokka.githubuser.utils.ConvertCollection
import com.pajokka.githubuser.utils.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FavoriteFragment : Fragment(), KodeinAware {
    private lateinit var userAdapter: UserAdapter
    override val kodein: Kodein by kodein()
    private val factory: ViewModelFactory by instance()
    private lateinit var viewModel: FavoriteViewModel

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = UserAdapter {
            val userDetail =
                Intent(context, DetailActivity::class.java).putExtra(DetailActivity.DATA_USER, it)
            startActivity(userDetail)
        }
        setRecyclerview()
        getDataFavorite()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDataFavorite() {
        viewModel.getAllFavoriteUser()?.observe(viewLifecycleOwner) {
            val userData = ConvertCollection().convertListToArraylist(it)
            CoroutineScope(Dispatchers.Main).launch {
                if (it != null) {
                    isUserNotFound(false)
                    userAdapter.setDataUser(userData)
                    userAdapter.notifyDataSetChanged()

                    if (userData.isEmpty()) {
                        isUserNotFound(true)
                    }
                }
            }
        }
    }

    private fun setRecyclerview() {
        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun isUserNotFound(state: Boolean) {
        when (state) {
            true -> {
                binding.avUser.visibility = View.VISIBLE
                binding.tvUser.visibility = View.VISIBLE
                binding.rvFavorite.visibility = View.GONE
            }
            false -> {
                binding.avUser.visibility = View.GONE
                binding.tvUser.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}