package co.edu.unicauca.lottieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.unicauca.lottieapp.databinding.FragmentQrBinding


class QrFragment : Fragment() {

    private var _binding: FragmentQrBinding? = null

    private val binding: FragmentQrBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQrBinding.inflate(inflater,container,false)
        return binding.root
    }

}