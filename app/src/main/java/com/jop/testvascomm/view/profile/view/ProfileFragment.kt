package com.jop.testvascomm.view.profile.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.jop.testvascomm.R
import com.jop.testvascomm.databinding.FragmentHomeBinding
import com.jop.testvascomm.databinding.FragmentProfileBinding
import com.jop.testvascomm.ui.BaseFragment

class ProfileFragment : BaseFragment(), MenuProvider {
    private lateinit var binding: FragmentProfileBinding
    private var isProfile: Boolean = true


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            isProfile = it.getString("type").toString() == "profile"
        }

        mainAct.setSupportActionBar(binding.toolbar.toolbar)
        mainAct.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.apply {
            toolbar.toolbar.setNavigationOnClickListener {
                mainAct.navController.popBackStack()
            }

            setupButtonSelector(isProfile)

            tvProfile.setOnClickListener { setupButtonSelector(true) }
            tvSetting.setOnClickListener { setupButtonSelector(false) }
        }
    }

    private fun setupButtonSelector(isProfile: Boolean){
        binding.apply {
            if (isProfile){
                tvProfile.setBackgroundResource(R.drawable.bg_green_rounded)
                tvSetting.setBackgroundResource(R.drawable.bg_white_rounded)
                toolbar.toolbar.title = "Profile Saya"
            } else {
                tvProfile.setBackgroundResource(R.drawable.bg_white_rounded)
                tvSetting.setBackgroundResource(R.drawable.bg_green_rounded)
                toolbar.toolbar.title = "Pengaturan"
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return  false
    }
}