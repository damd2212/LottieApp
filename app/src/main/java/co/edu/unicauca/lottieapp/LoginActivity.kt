package co.edu.unicauca.lottieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.unicauca.lottieapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}