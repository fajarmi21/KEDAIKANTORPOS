package com.polinema.android.kotlin.kedaikantorpos

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.polinema.android.kotlin.kedaikantorpos.admin.AdminActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore

    override fun onClick(v : View?) {
        when(v?.id){
            R.id.login -> {
                var email = edUserName.text.toString()
                var password = edPassword.text.toString()

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(this , "Username / Password can't be empety", Toast.LENGTH_LONG).show()
                }else{
                    val progressDialog = ProgressDialog(this)
                    progressDialog.isIndeterminate = true
                    progressDialog.setMessage("Authentic...")
                    progressDialog.show()


                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (!it.isSuccessful) return@addOnCompleteListener
                            else {
                                db.collection("users")
                                    .document(FirebaseAuth.getInstance().currentUser!!.uid).get()
                                    .addOnSuccessListener {
                                        val level = it.get("level").toString()
                                        when (level) {
                                            "admin" -> {
                                                val intent = Intent(this, AdminActivity::class.java)
                                                startActivity(intent)
                                            }
                                            "user" -> {
                                                val intent = Intent(this, MainActivity::class.java)
                                                startActivity(intent)
                                            }
                                        }
                                    }
                                    .addOnFailureListener {
                                        progressDialog.hide()
                                        Log.e("tag", it.message)
                                        Toast.makeText(this, "Cannot get user by username : $email", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                        .addOnFailureListener {
                            progressDialog.hide()
                            Log.e("tag", it.message)
                            Toast.makeText(this, "Username / password incorrect", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            R.id.back_to_register_textview ->{
                val intent = Intent (this,RegisterActivity::class.java)
                startActivity(intent)
            }

        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

    }
    private fun updateUI() {
        Toast.makeText(this, "You're logged in", Toast.LENGTH_SHORT).show()

        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
        back_to_register_textview.setOnClickListener(this)
        db = FirebaseFirestore.getInstance()

    }
}
