package co.edu.unicauca.lottieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.unicauca.lottieapp.databinding.FragmentAcercaBinding


class AcercaFragment : Fragment() {


    private var _binding: FragmentAcercaBinding? = null

    private val binding: FragmentAcercaBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAcercaBinding.inflate(inflater,container,false)

        return binding.root
    }


}