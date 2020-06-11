package com.polinema.android.kotlin.kedaikantorpos

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_keranjang.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.listMenuPelanggan
import kotlinx.android.synthetic.main.activity_main.pelanggan
import kotlinx.android.synthetic.main.login_dialog.view.*
import kotlinx.android.synthetic.main.row_menu.*
import kotlinx.android.synthetic.main.row_menu.view.*
import kotlinx.android.synthetic.main.row_menu.view.Imv
import kotlinx.android.synthetic.main.row_menu.view.btnKurang
import kotlinx.android.synthetic.main.row_menu.view.btnTambah
import kotlinx.android.synthetic.main.row_menu.view.psQuantity
import kotlinx.android.synthetic.main.row_menu.view.txDeskripsi
import kotlinx.android.synthetic.main.row_menu.view.txMenu
import kotlinx.android.synthetic.main.row_pesanan.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class KeranjangActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var storage: StorageReference
    lateinit var db: CollectionReference
    lateinit var db1: CollectionReference
    lateinit var adapter: CustomAdapter
    var uri = Uri.EMPTY
    val F_NAME = "file_name"
    val F_HARGA = "file_harga"
    val F_DESKRIPSI = "file_deskripsi"
    val F_TYPE = "file_type"
    val F_URL = "file_url"
    val F_IDT = "file_idt"
    val RC_OK = 100
    var fileType = ""
    var fileName = ""
    val F_TOTAL = "file_total"
    val F_QUANTITY = "file_quantity"
    val F_TANGGAL = "file_tanggal"
    val F_STATUS = "file_status"
    var email = ""

    var tahun = 0
    var bulan = 0
    var hari = 0
    val cal: Calendar = Calendar.getInstance()
    var quantity = 0
    lateinit var alFile: ArrayList<Note>
    var listNotes = ArrayList<Note>()


    var arrQ = ArrayList<String>()
    var arrH = ArrayList<String>()
    var arrI = ArrayList<String>()
    val arrY = ArrayList<String>()


    lateinit var auth: FirebaseAuth
    override fun onClick(v: View?) {

        when (v?.id) {
//            R.id.linkLogin ->{
//                var intent = Intent(this,LoginActivity::class.java)
//                startActivity(intent)
//            }


        }
    }

    private fun getUserProfile() {
        // [START get_user_profile]
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            email = user.email.toString()
            val photoUrl = user.photoUrl
            pelanggan.text = email
            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }
        // [END get_user_profile]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)
        alFile = ArrayList()
        uri = Uri.EMPTY

        auth = FirebaseAuth.getInstance()

//        listMenuPelanggan.setOnClickListener{
//            val currentUser = auth.currentUser
//            if (currentUser != null) {
//                updateUI()
//            }else{
//                val intent = Intent(this,LoginActivity::class.java)
//                startActivity(intent)
//            }
//        }

//        listMenuPelanggan.setOnItemClickListener(itemClick)

        keranjang.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                updateUI()
            } else {
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.login_dialog, null)
                //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Apakah Kamu Ingin Login ??")
                //show dialog
                val mAlertDialog = mBuilder.show()
                //login button click of custom layout
                mDialogView.dialogLoginBtn.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        updateUI()
                    } else {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    //get text from EditTexts of custom layout

                    //set the input text in TextView
//            mainInfoTv.setText("Name:"+ name +"\nEmail: "+ email +"\nPassword: "+ password)
                }
                //cancel button click of custom layout
                mDialogView.dialogCancelBtn.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }
            }

        }
    }

    val itemClick = AdapterView.OnItemClickListener { parent, view, position, id ->

        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI()
        } else {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.login_dialog, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Apakah Kamu Ingin Login ??")
            //show dialog
            val mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.dialogLoginBtn.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    updateUI()
                } else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                //get text from EditTexts of custom layout

                //set the input text in TextView
//            mainInfoTv.setText("Name:"+ name +"\nEmail: "+ email +"\nPassword: "+ password)
            }
            //cancel button click of custom layout
            mDialogView.dialogCancelBtn.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }

    }

    private fun updateUI() {
//        Toast.makeText(this, "You're logged in", Toast.LENGTH_SHORT).show()
//
//        val intent = Intent(this,PemesananActivity::class.java)
//        startActivity(intent)

        var trx = SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date())
        bulan = cal.get(Calendar.MONTH) + 1
        hari = cal.get(Calendar.DAY_OF_MONTH)
        tahun = cal.get(Calendar.YEAR)

        var tanggal = "Tanggal $hari - $bulan - $tahun"
        var st = "Hutang"

        storage = FirebaseStorage.getInstance().reference
        db = FirebaseFirestore.getInstance().collection("Transaksi")
        db.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e("firestore :", firebaseFirestoreException.message)
            }
            val hm = HashMap<String, Any>()
            hm.set(F_IDT, trx)
            hm.set(F_QUANTITY, arrQ)
            hm.set(F_NAME, pelanggan.text.toString())
            hm.set(F_TOTAL, txtotal.text.toString())
            hm.set(F_STATUS, st)
            hm.set(F_TANGGAL, tanggal)
            db.document(trx).set(hm).addOnSuccessListener {
                Toast.makeText(this, "Data Berhasil Di Tambahkan", Toast.LENGTH_SHORT).show()
                psQuantity.setText("0")
                txtotal.setText("0")
                val intent = Intent(this, PemesananActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Data Gagal Di Tambahkan : ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onStart() {
        super.onStart()

        getUserProfile()
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            updateUI()
//        }else{
//            val intent = Intent(this,LoginActivity::class.java)
//            startActivity(intent)
//        }
        total()





        db1 = FirebaseFirestore.getInstance().collection("keranjang")

        storage = FirebaseStorage.getInstance().reference
        db = FirebaseFirestore.getInstance().collection("menu")
        db.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e("firestore :", firebaseFirestoreException.message)
            }
            showData()
        }
    }

    fun total() {
//        var total = 0
//        for (i in 0..arrH.count() - 1) {
//            total = total + arrH.get(i).toInt() * arrQ.get(i).toInt()
//        }
//        txtotal.text = DecimalFormat("#.###").format(total)
    }

    fun showData() {
        arrH.clear()
        arrI.clear()
        arrQ.clear()
        arrY.clear()
        listNotes.clear()
        alFile.clear()
        var q = 0
        FirebaseFirestore.getInstance()
            .collection("keranjang")
            .whereEqualTo("file_name", email)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    var count = 0
                    for (document in it.result!!) {
                        count++
                    }
                    Log.w("count", count.toString())
                }
            }
        db.get().addOnSuccessListener { result ->
            val extras = intent.extras
            val doc = extras?.get("doc")
            db1
                .whereEqualTo("file_idt", doc)
                .whereEqualTo("file_name", email)
                .get()
                .addOnSuccessListener { result2 ->
                    for (doc1 in result2) {
//                val y = doc1.get(F_QUANTITY) as ArrayList<String>
                        for (doc2 in doc1.get(F_QUANTITY) as List<String>) {
//                        Log.d("i", doc2)
                            arrY.add(doc2)
                        }
                    }
                    for (doc in result) {
                        val menu = doc.get(F_NAME).toString()
                        val harga = doc.get(F_HARGA).toString()
                        val deskripsi = doc.get(F_DESKRIPSI).toString()
                        val url = doc.get(F_URL).toString()
                        Log.d("yy", arrY.get(q))
                        alFile.add(Note(harga, menu, deskripsi, url, arrY.get(q).toInt()))
                        arrQ.add(quantity.toString())
                        arrH.add(harga)
                        arrI.add(menu)
//                Log.d(this.toString(),"data - "+ q.toString())
                        q = q + 1
                    }
                    listMenuPelanggan.adapter = myAdaper(this, alFile)

                }
                .addOnFailureListener { exception ->
                    Log.w("error", "Error getting documents: ", exception)
                }
        }


//        var myNotesAdapter = myAdaper (this, listNotes)
//        listMenuPelanggan.adapter = myNotesAdapter

//        db.get().addOnSuccessListener { result ->
//            alFile.clear()
//            for (doc in result){
//                val hm = HashMap<String,Any>()
//                hm.put(F_NAME, doc.get(F_NAME).toString())
//                hm.put(F_HARGA, doc.get(F_HARGA).toString())
//                hm.put(F_DESKRIPSI, doc.get(F_DESKRIPSI).toString())
//                hm.put(F_URL, doc.get(F_URL).toString())
//                alFile.add(hm)
//            }
//            adapter = CustomAdapter(this,alFile)
//            listMenuPelanggan.adapter = adapter
//        }
    }

    inner class Note(
        nodeHarga: String,
        nodeMenu: String,
        nodeDeskripsi: String,
        nodeUrl: String,
        nodeQuantity: Int
    ) {
        var nodeHarga = nodeHarga
        var nodeMenu: String? = nodeMenu
        var nodeDeskripsi: String? = nodeDeskripsi
        var nodeUrl: String? = nodeUrl
        var nodeQuantity = nodeQuantity


    }

    inner class myAdaper : BaseAdapter {
        var listNoteAdapter = ArrayList<Note>()
        var context: Context? = null
        var totalA = 0


        constructor(context: Context, listNoteAdapter: ArrayList<Note>) : super() {
            this.listNoteAdapter = listNoteAdapter
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.row_pesanan, null)
            val myNote = listNoteAdapter[position]
            myView.rego1.text = myNote.nodeHarga
            myView.txMenu.text = myNote.nodeMenu
            myView.txDeskripsi.text = myNote.nodeDeskripsi
            myView.psQuantity.text = myNote.nodeQuantity.toString()

            totalA = totalA + myNote.nodeHarga.toInt() * myNote.nodeQuantity
            txtotalK.text = totalA.toString()
            Log.i("harga", totalA.toString())

//            var fileType = myNote.nodeType.toString()
            var url = Uri.parse(myNote.nodeUrl.toString())

//            when (fileType) {
//                ".jpg" -> {
//                    Picasso.get().load(url).into(myView.imvPesan)
//                }
//            }
            Picasso.get().load(url).into(myView.Imv)


            myView.btnTambah.setOnClickListener {
                if (quantity == 100) {
                    Toast.makeText(
                        this@KeranjangActivity,
                        "pesanan maksimal 100",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    quantity = arrQ.get(position).toInt() + 1
                    myView.psQuantity.text = quantity.toString()
                    arrQ.set(position, quantity.toString())
//                    Log.i(psNama.toString(), arrQ[position])
                }
                total()
            }

            myView.btnKurang.setOnClickListener {
                if (quantity == 0) {
                    Toast.makeText(this@KeranjangActivity, "pesanan minimal 1", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    quantity = arrQ.get(position).toInt() - 1
                    myView.psQuantity.text = quantity.toString()
                    arrQ.set(position, quantity.toString())
//                    Log.i(psNama.toString(), arrQ[position])
                }
                total()
            }
            return myView
        }

        override fun getItem(position: Int): Any {
            return listNoteAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNoteAdapter.size
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if ((resultCode == Activity.RESULT_OK) && (requestCode == RC_OK)){
//            if (data != null) {
//                uri = data.data
//                txSelectedFile.setText(uri.toString())
//            }
//        }
//    }
}
