package com.polinema.android.kotlin.kedaikantorpos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_pemesanan.*

class PemesananActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemesanan)
        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            updateUI()
//        }else{
//            val intent = Intent(this,LoginActivity::class.java)
//            startActivity(intent)
//        }
        getUserProfile()

//        storage = FirebaseStorage.getInstance().reference
//        db = FirebaseFirestore.getInstance().collection("menu")
//        db.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//            if (firebaseFirestoreException!=null){
//                Log.e("firestore :",firebaseFirestoreException.message)
//            }
//            showData()
//        }
    }


    private fun getUserProfile() {
        // [START get_user_profile]
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            txpengguna.text = email

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }
        // [END get_user_profile]
    }
}
