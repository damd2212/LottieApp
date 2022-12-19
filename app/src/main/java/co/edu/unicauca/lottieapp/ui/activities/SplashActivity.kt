package co.edu.unicauca.lottieapp.ui.activities

import android.animation.Animator
import android.content.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.edu.unicauca.lottieapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.splashAnimation.playAnimation()

        binding.splashAnimation.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
    }
}

