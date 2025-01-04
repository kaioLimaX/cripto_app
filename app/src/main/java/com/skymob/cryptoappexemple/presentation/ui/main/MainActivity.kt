package com.skymob.cryptoappexemple.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.skymob.cryptoappexemple.R
import com.skymob.cryptoappexemple.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
        }
        this.title = navController.currentDestination?.label

        binding.bottomNavigationView.itemActiveIndicatorColor = null
        binding.bottomNavigationView.itemTextColor = ContextCompat.getColorStateList(this, R.color.white)
        binding.bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.white)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.title = destination.label // Atualiza com o `label` definido no NavGraph
        }



    }


}