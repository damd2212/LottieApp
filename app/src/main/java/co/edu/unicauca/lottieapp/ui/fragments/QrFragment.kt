package co.edu.unicauca.lottieapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import co.edu.unicauca.lottieapp.R

import co.edu.unicauca.lottieapp.databinding.FragmentQrBinding
import com.google.zxing.integration.android.IntentIntegrator


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

    override fun onStart() {
        super.onStart()
        initScanner()
        //binding.btnFragmentQr.setOnClickListener { initScanner() }
    }

    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanea el codigo QR de la cancha")
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)

        if (result != null){
            if (result.contents == null){
                Toast.makeText(getActivity(), "Escaner cancelado", Toast.LENGTH_SHORT).show();
                findNavController().navigate(R.id.action_qrFragment_to_locationFragment)
            }else{
                Toast.makeText(getActivity(), "Valor escaneado: ${result.contents}", Toast.LENGTH_SHORT).show();
                setFragmentResult("key", bundleOf("idescenario" to result.contents))
                findNavController().navigate(R.id.action_qrFragment_to_infoQrFragment)
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)

        }

    }
}