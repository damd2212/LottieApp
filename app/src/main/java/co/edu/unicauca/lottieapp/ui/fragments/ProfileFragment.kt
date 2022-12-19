package co.edu.unicauca.lottieapp.ui.fragments

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import co.edu.unicauca.lottieapp.ui.activities.LoginActivity
import co.edu.unicauca.lottieapp.databinding.FragmentProfileBinding
import co.edu.unicauca.lottieapp.data.repository.UserRepository
import co.edu.unicauca.lottieapp.utils.Constants
import co.edu.unicauca.lottieapp.utils.checkPermission
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


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
        lifecycleScope.launch{
            userRepository.getCurrentUser()?.let {
                binding.nameProfileFragment.isVisible = true
                binding.nameProfileFragment.text = it.nombre
                binding.emailProfileFragment.text= it.userEmailAddress
                binding.idNumbProfileFragment.text = it.identificacion
                binding.codeNumbProfileFragment.text = it.codigo
                println("------From profile---------")
                println(it.image)
                println("------From profile---------")

                if (it.image != null){
                    Glide.with(binding.root).load(it.image).centerCrop().into(binding.imageProfileFragment)
                }

                binding.logoutFragmentButton.setOnClickListener {
                    userRepository.logout()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }?: run {

                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        binding.imageProfileFragment.setOnClickListener {
            if (this.checkPermission(Manifest.permission.CAMERA, Constants.CAMERA_PERMISSION)){
                openCamera()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.CAMERA_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            openCamera()
        } else{
            Snackbar.make(binding.root,"Permiso denegado",Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.TAKE_PICTURE){
            if (data != null && data.extras != null){
                val extras = data.extras!!
                val image = extras["data"] as Bitmap?
                if (image != null){
                    lifecycleScope.launch{
                        userRepository.uploadImage(image)
                        this@ProfileFragment.onStart()
                    }

                }
            }
        }
    }

    private fun openCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, Constants.TAKE_PICTURE)
        }catch (e: ActivityNotFoundException){

        }
    }

}