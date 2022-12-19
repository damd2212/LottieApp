package co.edu.unicauca.lottieapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.lottieapp.ui.activities.HomeActivity
import co.edu.unicauca.lottieapp.R
import co.edu.unicauca.lottieapp.databinding.FragmentSignUpBinding
import co.edu.unicauca.lottieapp.data.repository.UserRepository
import co.edu.unicauca.lottieapp.utils.isValidEmail
import co.edu.unicauca.lottieapp.utils.isValidPassword
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null

    private val binding: FragmentSignUpBinding get() = _binding!!
    private val userRepository by lazy { UserRepository() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.fragmentSignupButton.setOnClickListener {

            if (!binding.signupEmail.text.toString().isValidEmail()){
                binding.signupEmailLayout.error = getString(R.string.email_error)
            }else{
                binding.signupEmailLayout.error = null
            }

            if (!binding.signupPassword.text.toString().isValidPassword()){
                binding.signupPasswordLayout.error = getString(R.string.password_error)
            }else{
                binding.signupPasswordLayout.error = null
            }
            if (binding.signupEmail.text.toString().isValidEmail() && binding.signupPassword.text.toString().isValidPassword()){
                lifecycleScope.launch{
                    val result = userRepository.createUser(binding.signupEmail.text.toString(),binding.signupPassword.text.toString(),binding.signupName.text.toString(),binding.signupCod.text.toString(),binding.signupId.text.toString())
                    println(result)

                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()



                }


//                lifecycleScope.launch {
//                    val result = userRepository.createUser(binding.signupName.text.toString(),binding.signupEmail.text.toString(), binding.signupRadioGroup.checkedRadioButtonId.toString(), binding.signupPassword.text.toString())
//                    when(result){
//                        is Resource.Error -> Toast.makeText(requireContext(),"Error ${result.message}", Toast.LENGTH_LONG).show()
//                        is Resource.Loading -> {}
//                        is Resource.Success -> {
//
//                        }
//                    }
//                }
            }
        }

        binding.fragmentSignupLabel2.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }
}