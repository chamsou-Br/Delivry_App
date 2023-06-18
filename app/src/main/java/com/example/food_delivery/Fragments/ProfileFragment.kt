package com.example.food_delivery.Fragments

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.food_delivery.AuthActivity
import com.example.food_delivery.R
import com.example.food_delivery.Utils.DataType.clientData
import com.example.food_delivery.Utils.DataType.reviewRest
import com.example.food_delivery.Utils.url
import com.example.food_delivery.ViewModal.ClientModal
import com.example.food_delivery.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var clientModal : ClientModal

    lateinit var imageBitmap: Bitmap
    private lateinit var image: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        val pref = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
        if (pref.contains("connected")) {
            val pref = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE) // ref.getString("token_food_delivry","")!!
        }else {
            val intent = Intent(requireActivity(), AuthActivity::class.java)
            startActivity(intent)
        }
        clientModal = ViewModelProvider(requireActivity()).get(ClientModal::class.java)
        binding.apply {
            username.text = clientModal.client.value?.fullName
            fullName.setText(clientModal.client.value?.fullName)
            email.setText(clientModal.client.value?.email)
            phone.setText(clientModal.client.value?.phone)
            Address.setText(clientModal.client.value?.address)
            if (!clientModal.client.value?.picture.toString().contains("http")){
                Glide.with(requireActivity())
                    .load(url + "/" + clientModal.client.value?.picture)
                    .into(pictureProfile)
            }else {
                Glide.with(requireActivity())
                    .load(clientModal.client.value?.picture)
                    .into(pictureProfile)
            }
        }
        clientModal.loading.observe(requireActivity()) { load ->
            if (load == true) {
                binding.progressBarEditProfile.visibility = View.VISIBLE
                binding.editProfile.visibility = View.GONE
                binding.finishIcon.visibility  = View.GONE
            }else {
                binding.progressBarEditProfile.visibility = View.GONE
                binding.editProfile.visibility = View.VISIBLE
                binding.finishIcon.visibility  = View.VISIBLE
            }
        }

        binding.Editprofile.setOnClickListener {
            clientModal.editProfile(clientData(fullName = binding.fullName.text.toString(),email = binding.email.text.toString(), address = binding.Address.text.toString(), phone = binding.phone.text.toString(), token = clientModal.client.value?.token))


        }

        image = binding.pictureProfile;

        clientModal.errorMessage.observe(requireActivity()){err ->
            Toast.makeText(requireContext(), err, Toast.LENGTH_SHORT).show()
        }

        clientModal.client.observe(requireActivity()){err ->
            Toast.makeText(requireContext(), "your information is updated correctly", Toast.LENGTH_SHORT).show()
            binding.apply {
                username.text = clientModal.client.value?.fullName
                fullName.setText(clientModal.client.value?.fullName)
                email.setText(clientModal.client.value?.email)
                phone.setText(clientModal.client.value?.phone)
                Address.setText(clientModal.client.value?.address)
                if (!clientModal.client.value?.picture.toString().contains("http")){
                    Glide.with(requireActivity())
                        .load(url + "/" + clientModal.client.value?.picture)
                        .into(pictureProfile)
                }else {
                    Glide.with(requireActivity())
                        .load(clientModal.client.value?.picture)
                        .into(pictureProfile)
                }
            }
        }

        binding.pictureEdit.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }


    }



        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if ( resultCode == Activity.RESULT_OK) {
                println( data?.data)
                val imageUri: Uri? = data?.data

                val selectedImageUri = data?.getData()
                imageBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(requireActivity().contentResolver, selectedImageUri!!)
                    ImageDecoder.decodeBitmap(source)
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImageUri)
                }
                image.setImageBitmap(imageBitmap)


                if (imageUri != null) {
                    // Perform the image upload using Retrofit
                    clientModal.uploadPicture(imageUri,imageBitmap,clientModal.client.value?.token.toString(),requireActivity())
                }
            }
        }
    }


