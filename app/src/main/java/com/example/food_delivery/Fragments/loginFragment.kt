package com.example.food_delivery.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.food_delivery.MainActivity
import com.example.food_delivery.R
import com.example.food_delivery.Utils.DataType.clientData
import com.example.food_delivery.ViewModal.ClientModal
import com.example.food_delivery.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class loginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var clientMod : ClientModal

    private lateinit var client : GoogleSignInClient;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val pref = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
        val fragmentIndex = pref.getInt("fragmentIndex",0);
        println(fragmentIndex)
        val pref2 = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE).edit()
        pref2.remove("fragmentIndex");
        pref2.apply()
        binding = FragmentLoginBinding.inflate(layoutInflater)
        clientMod = ViewModelProvider(requireActivity()).get(ClientModal::class.java)
        binding.toRegister.setOnClickListener{
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.signin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val client =  clientData(email = email, password = password);
            clientMod.login(client)
        }
        // loading observer
        clientMod.loading.observe(requireActivity(), { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.signTitle.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.signTitle.visibility = View.VISIBLE
            }
        })

        clientMod.errorMessage.observe(requireActivity(), { errorMessaage ->
            Toast.makeText(requireContext(), errorMessaage, Toast.LENGTH_SHORT).show()
        })

        clientMod.isConnected.observe(requireActivity()) { isConnect ->
            val pref =
                requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE).edit()
            if (isConnect == true) {
                pref.putBoolean("connected", true)
                pref.putString("token_food_delivry", clientMod.client.value?.token)
                pref.apply()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                if (fragmentIndex == 1) {
                    intent.putExtra("fragmentIndex", 1)
                }else {
                    intent.putExtra("fragmentIndex", 0)
                }
                startActivity(intent)
            } else {
                pref.remove("connected");
                pref.apply()
            }
        }


        // signi with google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestProfile()
            .build()
        client = GoogleSignIn.getClient(requireContext(), gso);
        binding.signinWithGoogle.setOnClickListener {
            val signInIntent: Intent = client.getSignInIntent()
            startActivityForResult(signInIntent, 10001)

        }
        binding.signinWithFb.setOnClickListener {
            client.signOut()
        }


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 10001) {
            // The Task returned from this call is always completed, no need to attach a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            val client =  clientData(email = account.email.toString(), fullName = account.familyName.toString(), googleIdToken = account.idToken, picture =  account.photoUrl.toString());
            println(client)
            println("account.photoUr")
            println(account.idToken)
            clientMod.loginWithGoogle(client)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(requireContext(), "An error has occurred :(", Toast.LENGTH_SHORT).show()
            println(e)
        }
    }

    /*
    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            val i  = Intent(requireActivity(),MainActivity::class.java)
            startActivity(i)
        }
    }

     */



}