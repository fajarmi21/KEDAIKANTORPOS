package com.polinema.android.kotlin.kedaikantorpos



import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_inputdatamenu.*
import kotlinx.android.synthetic.main.activity_pertanyaan.*
import java.util.*
import kotlin.collections.HashMap

class inputdatamenu : AppCompatActivity(), View.OnClickListener {

    lateinit var storage : StorageReference
    lateinit var db : CollectionReference
    lateinit var db1 : CollectionReference
    lateinit var alFile : ArrayList<HashMap<String, Any>>
    lateinit var adapter: CustomAdapter
    var uri = Uri.EMPTY
    val F_COUNT = ""
    val F_NAME  = "file_name"
    val F_HARGA  = "file_harga"
    val F_DESKRIPSI  = "file_deskripsi"
    val F_TYPE  = "file_type"
    var KT = ""
    var KU = ""
    val F_URL  = "file_url"
    val RC_OK = 100
    var fileType = ""
    var fileDeskripsi = ""
    var fileName = ""
    var fileMenu = ""
    var fileRego = ""
    val Kri1 = "kriteriatoping"
    var Kri2 = "kriteriaukuran"
    var Kri3 = "kriteriaharga"

    override fun onStart() {
        super.onStart()
        storage = FirebaseStorage.getInstance().reference
        db = FirebaseFirestore.getInstance().collection("menu")
        db.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException!=null){
                Log.e("firestore :",firebaseFirestoreException.message)
            }
            showData()
        }
    }


    override fun onClick(v: View?) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        when(v?.id){
            R.id.btnUpImg   ->{
                fileType = ".jpg"
                intent.setType("image/*")}
            R.id.btnUpload  ->{
                if (uri != null){
                    fileName = txNamaMenu.text.toString()
                    val fileRef = storage.child(fileName+fileType)
//                    fileMenu = txNamaMenu.text.toString()
                    var spinner2 = findViewById<Spinner>(R.id.k2)

                                if  (spinner2.selectedItem == "1 Toping"){
                                    val jika: Double
                                    jika = 1.0 / 3.0
                                    KT = jika.toString()
                                    Log.e("ya", jika.toString())
                                }
                                else if (spinner2.selectedItem == "2 Toping"){
                                    val jika: Double
                                    jika = 2.0 / 3.0
                                    KT = jika.toString()
                                    Log.e("ya", jika.toString())
                                }
                                else if (spinner2.selectedItem == "3 Toping"){
                                    val jika: Double
                                    jika = 3.0 / 3.0
                                    KT = jika.toString()
                                    Log.e("ya", jika.toString())
                                }
                    var spinner3 = findViewById<Spinner>(R.id.k1)
                                if  (spinner3.selectedItem == "Mini"){
                                    val jika: Double
                                    jika = 1.0 / 3.0
                                    KU = jika.toString()
                                    Log.e("ya", jika.toString())

                                }
                                else if (spinner3.selectedItem == "Sedang"){
                                    val jika: Double
                                    jika = 2.0 / 3.0
                                    KU = jika.toString()
                                    Log.e("ya", jika.toString())

                                }
                                else if (spinner3.selectedItem == "Jumbo"){
                                    val jika: Double
                                    jika = 3.0 / 3.0
                                    KU = jika.toString()
                                    Log.e("ya", jika.toString())

                                }



                    fileDeskripsi = txDeskrpsi.text.toString()
                    fileRego = input_rego.text.toString()

                    var kHarga = 7000.0 / input_rego.text.toString().toDouble()
                    fileRef.putFile(uri)
                        .continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                            return@Continuation fileRef.downloadUrl
                        })
                        .addOnCompleteListener { task ->
                            val hm = HashMap<String, Any>()
                            hm.put(F_NAME, fileName)
                            hm.put(F_HARGA, fileRego)
                            hm.put(F_DESKRIPSI, fileDeskripsi)
                            hm.put(Kri3, kHarga)
                            hm.put(Kri1, KT)
                            hm.put(Kri2, KU)

                            hm.put(F_URL, task.result.toString())
                            db.document(fileName).set(hm).addOnSuccessListener {
                                Toast.makeText(
                                    this,"File succesfully uploaded", Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
        }
        if (v?.id != R.id.btnUpload) startActivityForResult(intent, RC_OK)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inputdatamenu)

        btnUpImg.setOnClickListener(this)
        btnUpload.setOnClickListener(this)
        alFile = ArrayList()
        uri = Uri.EMPTY

        val kriteriatoping = resources.getStringArray(R.array.KriteriaToping)
        val kriteriaukuran = resources.getStringArray(R.array.KriteriaUkuran)

        var spinner2 = findViewById<Spinner>(R.id.k2)
        if (spinner2 != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, kriteriatoping
            )
            spinner2.adapter = adapter

            spinner2.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@inputdatamenu,
                        getString(R.string.selected_item) + " " +
                                "" + kriteriatoping[position], Toast.LENGTH_SHORT
                    ).show()
                    if  (spinner2.selectedItem == "1 Toping"){
                        val jika: Double
                        jika = 1.0 / 3.0

                        Log.e("ya", jika.toString())
                    }
                    else if (spinner2.selectedItem == "2 Toping"){
                        val jika: Double
                        jika = 2.0 / 3.0

                        Log.e("ya", jika.toString())
                    }
                    else if (spinner2.selectedItem == "3 Toping"){
                        val jika: Double
                        jika = 3.0 / 3.0

                        Log.e("ya", jika.toString())
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        val spinner3 = findViewById<Spinner>(R.id.k1)
        if (spinner3 != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, kriteriaukuran
            )
            spinner3.adapter = adapter

            spinner3.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@inputdatamenu,
                        getString(R.string.selected_item) + " " +
                                "" + kriteriaukuran[position], Toast.LENGTH_SHORT
                    ).show()
                    if  (spinner3.selectedItem == "Mini"){
                        var hasil = (1/3).toDouble()

                    }
                    else if (spinner3.selectedItem == "Sedang"){
                        var hasil = (1/3).toDouble()

                    }
                    else if (spinner3.selectedItem == "Jumbo"){
                        var hasil = (1/3).toDouble()

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }



    }




    fun showData(){
        db.get().addOnSuccessListener { result ->
            alFile.clear()
            for (doc in result){
                val hm = HashMap<String,Any>()
                hm.put(F_NAME, doc.get(F_NAME).toString())
                hm.put(F_HARGA, doc.get(F_HARGA).toString())
                hm.put(F_DESKRIPSI, doc.get(F_DESKRIPSI).toString())
                hm.put(F_URL, doc.get(F_URL).toString())
                alFile.add(hm)
            }
            adapter = CustomAdapter(this,alFile)
            lsV.adapter = adapter
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((resultCode == Activity.RESULT_OK) && (requestCode == RC_OK)){
            if (data != null) {
                uri = data.data
                txSelectedFile.setText(uri.toString())
            }
        }
    }
}
