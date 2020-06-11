package com.polinema.android.kotlin.kedaikantorpos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_pertanyaan.*

class Pertanyaan : AppCompatActivity() {


    lateinit var db : CollectionReference


    override fun onStart() {
        super.onStart()

        db = FirebaseFirestore.getInstance().collection("menu")
        val L = arrayListOf<String>()

        db.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            for (doc in querySnapshot!!.documents) {
                L.add(doc["file_harga"].toString())
            }
            Log.e("Coba :",L.toString())
            if (firebaseFirestoreException!=null){
                Log.e("firestore :",firebaseFirestoreException.message)
            }
        }
        txcb3.setOnClickListener{

            val intent = Intent(this,
                MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pertanyaan)

        var harga = 0
        var topping = 0
        var ukuran = 0

        hargaP.helperText = (100 - (ukuran + topping)).toString()
        topingP.helperText = (100 - (harga + ukuran)).toString()
        ukuranP.helperText = (100 - (harga + topping)).toString()

        txHargaP.filters = arrayOf(InputFilter.LengthFilter(3))
        txTopingP.filters = arrayOf(InputFilter.LengthFilter(3))
        txUkuranP.filters = arrayOf(InputFilter.LengthFilter(3))

        txHargaP.doOnTextChanged { text, _, _, _ ->
            harga = if (!text.isNullOrEmpty()) text.toString().toInt() else 0
            topping = if (!txTopingP.text.isNullOrEmpty()) txTopingP.text.toString().toInt() else 0
            ukuran = if (!txUkuranP.text.isNullOrEmpty()) txUkuranP.text.toString().toInt() else 0

            txHargaP.filters = arrayOf(MinMaxFilter(0, 100 - (ukuran + topping)), InputFilter.LengthFilter(3))
            txTopingP.filters = arrayOf(MinMaxFilter(0, 100 - (harga + ukuran)), InputFilter.LengthFilter(3))
            txUkuranP.filters = arrayOf(MinMaxFilter(0, 100 - (harga + topping)), InputFilter.LengthFilter(3))

            hargaP.helperText = (100 - (ukuran + topping) - harga).toString()
            topingP.helperText = (100 - (harga + ukuran) - topping).toString()
            ukuranP.helperText = (100 - (harga + topping) - ukuran).toString()
        }

        txTopingP.doOnTextChanged { text, _, _, _ ->
            harga = if (!txHargaP.text.isNullOrEmpty()) txHargaP.text.toString().toInt() else 0
            topping = if (!text.isNullOrEmpty()) text.toString().toInt() else 0
            ukuran = if (!txUkuranP.text.isNullOrEmpty()) txUkuranP.text.toString().toInt() else 0

            txHargaP.filters = arrayOf(MinMaxFilter(0, 100 - (ukuran + topping)), InputFilter.LengthFilter(3))
            txTopingP.filters = arrayOf(MinMaxFilter(0, 100 - (harga + ukuran)), InputFilter.LengthFilter(3))
            txUkuranP.filters = arrayOf(MinMaxFilter(0, 100 - (harga + topping)), InputFilter.LengthFilter(3))

            hargaP.helperText = (100 - (ukuran + topping) - harga).toString()
            topingP.helperText = (100 - (harga + ukuran) - topping).toString()
            ukuranP.helperText = (100 - (harga + topping) - ukuran).toString()
        }

        txUkuranP.doOnTextChanged { text, _, _, _ ->
            harga = if (!txHargaP.text.isNullOrEmpty()) txHargaP.text.toString().toInt() else 0
            topping = if (!txTopingP.text.isNullOrEmpty()) txTopingP.text.toString().toInt() else 0
            ukuran = if (!text.isNullOrEmpty()) text.toString().toInt() else 0

            txHargaP.filters = arrayOf(MinMaxFilter(0, 100 - (ukuran + topping)), InputFilter.LengthFilter(3))
            txTopingP.filters = arrayOf(MinMaxFilter(0, 100 - (harga + ukuran)), InputFilter.LengthFilter(3))
            txUkuranP.filters = arrayOf(MinMaxFilter(0, 100 - (harga + topping)), InputFilter.LengthFilter(3))

            hargaP.helperText = (100 - (ukuran + topping) - harga).toString()
            topingP.helperText = (100 - (harga + ukuran) - topping).toString()
            ukuranP.helperText = (100 - (harga + topping) - ukuran).toString()
        }
    }
}
