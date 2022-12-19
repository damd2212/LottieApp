package co.edu.unicauca.lottieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.FragmentForgotBinding
import co.edu.unicauca.lottieapp.data.repository.UserRepository
import co.edu.unicauca.lottieapp.utils.isValidEmail

class ForgotFragment : Fragment() {

    private var _binding: FragmentForgotBinding? = null

    private val binding: FragmentForgotBinding get() = _binding!!
    private val userRepository by lazy { UserRepository() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.fragmentForgotButton.setOnClickListener {
            if (!binding.forgotEmail.text.toString().isValidEmail()){
                binding.forgotEmailLayout.error = getString(R.string.password_error)

            }else{
                binding.forgotEmailLayout.error = null
                userRepository.sendResetPassword(binding.forgotEmail.text.toString())
                Toast.makeText(requireContext(),"se enviaron instrucciones a tu correo para recuperar la clave", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        }

        binding.fragmentForgotLabel2.setOnClickListener {
            findNavController().navigate(R.id.action_forgotFragment_to_signUpFragment)
        }
    }


}