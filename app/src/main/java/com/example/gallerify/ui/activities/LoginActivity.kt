package com.example.gallerify.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gallerify.R
import com.example.gallerify.repositories.ImageRepository
import com.example.gallerify.utils.Constants.REQUEST_CODE_GOOGLE_SIGN_IN
import com.example.gallerify.utils.Constants.TAG_LoginActivity
import com.example.gallerify.utils.resources.Resource
import com.example.gallerify.utils.displayToast
import com.example.gallerify.viewmodels.LoginViewModel
import com.example.gallerify.viewmodels.LoginViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val repo = ImageRepository()
        viewModel = ViewModelProvider(this,
            LoginViewModelFactory(repo)
        ).get(LoginViewModel::class.java)
        setListeners()
        viewModel.currentUser.observe(this, Observer {
            when(it){
                is Resource.Success ->{
                    startActivity(Intent(this, GalleryActivity::class.java))
                }
            }
        })
    }

    private fun setListeners() {
        btSignIn.setOnClickListener {
            startSignInFlow()
        }
    }

    private fun startSignInFlow() {
        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var token: String? = null
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    token = it.idToken
                }
                viewModel.firebaseAuthWithGoogle(token!!)
            } catch (e: Exception) {
                displayToast(R.string.something_went_wrong)
                Log.d(TAG_LoginActivity, e.toString())
            }
        }
    }
}