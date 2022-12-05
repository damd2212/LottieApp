package co.edu.unicauca.lottieapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import co.edu.unicauca.lottieapp.databinding.FragmentProfileBinding
import co.edu.unicauca.lottieapp.repository.UserRepository


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding: FragmentProfileBinding get() = _binding!!
    private val userRepository by lazy { UserRepository() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        userRepository.getCurrentUser()?.let {
            binding.name.isVisible = true
            binding.name.text = it.email
            binding.profileFragmentButton.text = "Cerrar Sesión"
            binding.profileFragmentButton.setOnClickListener {
                userRepository.logout()
                val intent = Intent(requireContext(),LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }?: run {

                val intent = Intent(requireContext(),LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
    }

}