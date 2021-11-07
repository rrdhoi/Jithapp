package com.pajokka.githubuser.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pajokka.githubuser.R
import com.pajokka.githubuser.databinding.SettingsFragmentBinding
import com.pajokka.githubuser.utils.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SettingsFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val factory: ViewModelFactory by instance()
    private lateinit var viewModel: SettingsViewModel

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(SettingsViewModel::class.java)
        darkMode()
    }

    private fun darkMode() {
        var isDarkMode = false

        viewModel.getThemeSettings().observe(viewLifecycleOwner, { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.ivDarkmode.setImageResource(R.drawable.ic_moon)
                binding.tvDarkmode.text = resources.getText(R.string.on_light_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.ivDarkmode.setImageResource(R.drawable.ic_sun)
                binding.tvDarkmode.text = resources.getText(R.string.on_dark_mode)
            }
        })

        binding.ivDarkmode.setOnClickListener {
            isDarkMode = !isDarkMode
            viewModel.saveThemeSetting(isDarkMode)
        }
    }

}