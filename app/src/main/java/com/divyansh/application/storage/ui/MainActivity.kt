package com.divyansh.application.storage.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.divyansh.application.storage.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val getContent = registerForActivityResult(MyOpenDocumentContract()) { uri: Uri? ->
        // Handle the returned Uri
        uri?.let {
            onImageSelected(it)
        }
        Log.d(TAG, uri.toString())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        auth.signInAnonymously()

        val user = Firebase.auth.currentUser


        binding.buttonSelectImage.setOnClickListener {
            if (user != null){
                getContent.launch(arrayOf("image/*"))
            }

        }
    }

    private fun onImageSelected(uri: Uri){
        viewModel.uploadImageRequestBuilder(uri)
    }
}