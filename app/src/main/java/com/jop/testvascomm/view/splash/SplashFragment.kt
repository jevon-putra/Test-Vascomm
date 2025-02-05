package com.jop.testvascomm.view.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jop.testvascomm.R
import com.jop.testvascomm.databinding.FragmentSplashBinding
import com.jop.testvascomm.ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            appData.getToken().let {
                if(it.isNullOrEmpty()){
                    mainAct.navController.navigate(
                        resId = R.id.action_splashFragment_to_loginFragment,
                    )
                } else {
                    mainAct.navController.navigate(
                        resId = R.id.action_splashFragment_to_homeFragment
                    )
                }
            }
        }
    }
}