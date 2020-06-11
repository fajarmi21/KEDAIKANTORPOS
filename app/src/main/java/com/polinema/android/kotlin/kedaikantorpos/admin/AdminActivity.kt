package com.polinema.android.kotlin.kedaikantorpos.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.polinema.android.kotlin.kedaikantorpos.MainActivity
import com.polinema.android.kotlin.kedaikantorpos.R
import com.polinema.android.kotlin.kedaikantorpos.inputdatamenu
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        loggoutAdmin.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        datainput.setOnClickListener{

            val intent = Intent(this,
                inputdatamenu::class.java)
            startActivity(intent)
        }
        kelola_pembayaran.setOnClickListener{

            val intent = Intent(this,
                pembayaranadmin::class.java)
            startActivity(intent)
        }
    }

}
