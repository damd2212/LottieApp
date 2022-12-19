package co.edu.unicauca.lottieapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.homeToolbar)
    }


    override fun onStart() {
        super.onStart()
        val navController = findNavController(R.id.nav_host_home_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.locationFragment,
                R.id.qrFragment,
                R.id.profileFragment,
                R.id.acercaFragment
            )
        )

        binding.bottomNavigation.setupWithNavController(navController)
        binding.homeToolbar.setupWithNavController(navController,appBarConfiguration)
    }
}