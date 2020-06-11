package com.polinema.android.kotlin.kedaikantorpos

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    companion object {
        val TAG = "RegisterActivity"
    }

    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        register_button_register.setOnClickListener {
            performRegister()
        }

        already_have_account_text_view.setOnClickListener {
            Log.d(TAG, "Try to show login activity")

            // launch the login activity somehow
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

//        selectphoto_button_register.setOnClickListener {
//            Log.d(TAG, "Try to show photo selector")
//
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            startActivityForResult(intent, 0)
//        }


    }

    var selectedPhotoUri: Uri? = null

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
//            // proceed and check what the selected image was....
//            Log.d(TAG, "Photo was selected")
//
//            selectedPhotoUri = data.data
//
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
//
////            selectphoto_imageview_register.setImageBitmap(bitmap)
////
////            selectphoto_button_register.alpha = 0f
//
////      val bitmapDrawable = BitmapDrawable(bitmap)
////      selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
//        }
//    }

    private fun performRegister() {
        val email = email_edittext_register.text.toString()
        val password = password_edittext_register.text.toString()
        val username = txaUsername.text.toString()


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "Attempting to create user with email: $email")

        // Firebase Authentication to create a user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser!!.uid
                    val data = hashMapOf(
                        "username" to username,
                        "email" to auth.currentUser!!.email,
                        "level" to "user"
                    )
//                uploadImageToFirebaseStorage()

                    db.collection("users").document(user).set(data)
                        .addOnSuccessListener {
                            Log.d("sukses", "DocumentSnapshot added with ID: ${user}")
                            Toast.makeText(
                                baseContext, "DocumentSnapshot added with ID: ${user}",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Failed to create user: ${it.message}")
                            Toast.makeText(
                                this,
                                "Failed to create user: ${it.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }else {
                    // If sign in fails, display a message to the user.
                    Log.w("gagal", "createUserWithEmail:failure", it.exception)
                    Toast.makeText(
                        baseContext, "Added user failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
//    private fun uploadImageToFirebaseStorage() {
//        if (selectedPhotoUri == null) return
//
//        val filename = UUID.randomUUID().toString()
//        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
//
//        ref.putFile(selectedPhotoUri!!)
//            .addOnSuccessListener {
//                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")
//
//                ref.downloadUrl.addOnSuccessListener {
//                    Log.d(TAG, "File Location: $it")
//
////                    saveUserToFirebaseDatabase(it.toString())
//                }
//            }
//            .addOnFailureListener {
//                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
//            }
//    }

//    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
//        val uid = FirebaseAuth.getInstance().uid ?: ""
//        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
//
////        val user = User(uid, username_edittext_register.text.toString(), profileImageUrl)
//
//        ref.setValue(user)
//            .addOnSuccessListener {
//                Log.d(TAG, "Finally we saved the user to Firebase Database")
//            }
//            .addOnFailureListener {
//                Log.d(TAG, "Failed to set value to database: ${it.message}")
//            }
//    }

    }
}

//class User(val uid: String, val username: String, val profileImageUrl: String)