package com.polinema.android.kotlin.kedaikantorpos.admin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.polinema.android.kotlin.kedaikantorpos.MainActivity
import com.polinema.android.kotlin.kedaikantorpos.R
import kotlinx.android.synthetic.main.activity_pembayaranadmin.*
import java.text.NumberFormat
import java.util.*

class pembayaranadmin : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaranadmin)

        alFile = ArrayList()
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
    override fun onClick(v: View?) {
        when(v?.id){
//            R.id.mlebu->{
//                val intent = Intent(this,detail_transaksi::class.java)
//                intent.putExtra("doc", tsTotal.toString())
//                startActivity(intent)
//                Log.e("ada", "doc")
//
//            }

        }
    }
    lateinit var thisParent : MainActivity
    lateinit var v : View
//    var model: communicator? = null


    lateinit var adapter: AdapterPesanan
    lateinit var alFile : ArrayList<HashMap<String, Any>>
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
        db.whereEqualTo("file_status", "Hutang").get().addOnSuccessListener { result ->
            alFile.clear()
            for (doc in result){
                val hm = HashMap<String,Any>()
                hm.put(F_IDT, doc.get(F_IDT).toString())
                hm.put(F_TANGGAL, doc.get(F_TANGGAL).toString())
                hm.put(F_TOTAL, doc.get(F_TOTAL).toString())

                hm.put(F_PEMBAYARAN, doc.get(F_PEMBAYARAN).toString())
//                hm.put(F_URL, doc.get(F_URL).toString())
                alFile.add(hm)
            }
            adapter = AdapterPesanan(this,alFile)
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
}
