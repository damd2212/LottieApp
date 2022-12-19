package co.edu.unicauca.lottieapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.lottieapp.ui.activities.HomeActivity
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.FragmentLoginBinding
import co.edu.unicauca.lottieapp.data.repository.Resource
import co.edu.unicauca.lottieapp.data.repository.UserRepository
import co.edu.unicauca.lottieapp.utils.isValidEmail
import co.edu.unicauca.lottieapp.utils.isValidPassword
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private var _binding:FragmentLoginBinding? = null

    private val binding:FragmentLoginBinding get() = _binding!!
    private val userRepository by lazy { UserRepository() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.fragmentLoginButton.setOnClickListener {
            if (!binding.loginEmail.text.toString().isValidEmail()){
                binding.loginEmailLayout.error = getString(R.string.email_error)
            }else{
                binding.loginEmailLayout.error = null
            }

            if (!binding.loginPassword.text.toString().isValidPassword()){
                binding.loginPasswordLayout.error = getString(R.string.password_error)
            }else{
                binding.loginPasswordLayout.error = null
            }

            if (binding.loginEmail.text.toString().isValidEmail() && binding.loginPassword.text.toString().isValidPassword()){

                lifecycleScope.launch {
                    val result = userRepository.login(binding.loginEmail.text.toString(),binding.loginPassword.text.toString())
                    when(result){
                        is Resource.Error -> Toast.makeText(requireContext(),"Error ${result.message}", Toast.LENGTH_LONG).show()
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            val intent = Intent(requireContext(), HomeActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    }
                }
            }
        }

        binding.fragmentLoginLabel2.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)

        }

        binding.loginForgotButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
        }
    }

}