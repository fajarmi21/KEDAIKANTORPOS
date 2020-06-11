package com.polinema.android.kotlin.kedaikantorpos

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pemesanan.*

import kotlinx.android.synthetic.main.item_transaksi.view.*
import kotlinx.android.synthetic.main.transaksi_activity.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class TransaksiActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var thisParent : MainActivity
    lateinit var v : View
//    var model: communicator? = null


    lateinit var adapter: AdapterTransaksi
    lateinit var alFile : java.util.ArrayList<HashMap<String, Any>>
    lateinit var db : CollectionReference
    lateinit var storage : StorageReference
    var uri = Uri.EMPTY

    val F_HARGA  = "file_harga"
    val F_NAME = "file_name"
    val F_TYPE = "file_type"
    val F_URL = "file_url"
    val F_IDT = "file_idt"
    val F_NAMA = "file_nama"
    val F_TANGGAL = "file_tanggal"
    val F_PEMBAYARAN = "file_status"
    val F_TOTAL = "file_total"
    val F_QUANTITY = "file_quantity"


//    val itemClick = AdapterView.OnItemClickListener { parent, view, position, id ->
//        val idtrx = view.findViewById<View>(R.id.tsIDT) as TextView
//        Log.i(this.toString(), idtrx.text.toString())
//        model!!.setMsgCommunicator(idtrx.text.toString())
//
//
//    }
    fun showData(){
        db.whereEqualTo("file_name", FirebaseAuth.getInstance().currentUser!!.email.toString()).get().addOnSuccessListener { result ->
            alFile.clear()
            for (doc in result){
                val hm = HashMap<String,Any>()
//                hm.put(F_NAME, doc.get(F_NAME).toString())
                hm.put("file_idt", doc.get("file_idt").toString())
                hm.put(F_TANGGAL, doc.get(F_TANGGAL).toString())
                hm.put(F_TOTAL, doc.get(F_TOTAL).toString())

               hm.put(F_PEMBAYARAN, doc.get(F_PEMBAYARAN).toString())
//                hm.put(F_URL, doc.get(F_URL).toString())
                alFile.add(hm)
            }
            adapter = AdapterTransaksi(this,alFile)
            ListTransaksi.adapter = adapter
        }
    }

//    inner class List(nodeIdt : String,nodeNama: String, nodeTotal: String, nodeTanggal: String){
//        var nodeIdt:String?= nodeIdt
//        var nodeNama:String?= nodeNama
//        var nodeTotal:String = nodeTotal
//        var nodeTanggal : String? = nodeTanggal
//
//    }
    fun rupiah(number: String): String{
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number.toDouble()).toString()
    }

//    inner class MyNotesAdapter : BaseAdapter {
//        var listAdapter = ArrayList<List>()
//        var context: Context? = null
//
//        constructor(context: Context, listAdapter: ArrayList<List>) : super() {
//            this.listAdapter = listAdapter
//            this.context = context
//        }
//
//        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            val e = position + 1
//            var myView = layoutInflater.inflate(R.layout.item_transaksi, null)
//            var myList = listAdapter[position]
//
//            myView.tsNomer.text = e.toString()
//            myView.tsIDT.text = myList.nodeIdt
//            myView.tsNama.text = myList.nodeNama
//            myView.tsTotal.text = rupiah(myList.nodeTotal)
//            myView.tsTanggal.text = myList.nodeTanggal
//
//            return myView
//        }
//
//        override fun getItem(position: Int): Any {
//            return listAdapter[position]
//        }
//
//        override fun getItemId(position: Int): Long {
//            return position.toLong()
//        }
//
//        override fun getCount(): Int {
//            return listAdapter.size
//        }
//    }

    override fun onClick(v: View?) {

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        var mnuInflater = menuInflater
//        mnuInflater.inflate(R.menu.menu_activity, menu)
//        return super.onCreateOptionsMenu(menu)
//
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item?.itemId){
////            R.id.MTambah -> {
////                val intent = Intent(this,MenuActivity::class.java)
////                startActivity(intent)
////            }
//            R.id.Mpesan -> {
//                val intent = Intent(this,MejaActivity::class.java)
//                startActivity(intent)
//            }
//            R.id.Mtransaksi ->{
//                val intent = Intent(this,TransaksiActivity::class.java)
//                startActivity(intent)
//            }
//            R.id.Mlogout ->{
//                FirebaseAuth.getInstance().signOut()
//                val intent = Intent(this,LoginActivity::class.java)
//                startActivity(intent)
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaksi_activity)
//        ListTransaksi.setOnItemClickListener(itemClick)



        alFile = java.util.ArrayList()
        uri = Uri.EMPTY
    }

    override fun onStart() {
        super.onStart()

        storage = FirebaseStorage.getInstance().reference
        db = FirebaseFirestore.getInstance().collection("keranjang")
        db.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException!=null){
                Log.e("firestore :",firebaseFirestoreException.message)
            }
            showData()
        }
    }
}