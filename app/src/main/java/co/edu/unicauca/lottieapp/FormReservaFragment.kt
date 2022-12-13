package co.edu.unicauca.lottieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.lottieapp.databinding.FragmentFormReservaBinding
import co.edu.unicauca.lottieapp.databinding.FragmentInfoQrBinding

class FormReservaFragment : Fragment() {

    private var _binding: FragmentFormReservaBinding? = null

    private val binding: FragmentFormReservaBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFormReservaBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setFragmentResultListener("keyForm"){ requestKey, bundle ->
                val result = bundle.getString("idEscenarioForm")
                println("resultado" + result)
        }
    }

}