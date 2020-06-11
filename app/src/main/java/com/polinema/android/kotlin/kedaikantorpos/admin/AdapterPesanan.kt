package com.polinema.android.kotlin.kedaikantorpos.admin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.polinema.android.kotlin.kedaikantorpos.R

class AdapterPesanan(val context : Context,
                     arrayList: ArrayList<HashMap<String,Any>>) : BaseAdapter() {
    val F_NAME  = "file_name"
    val F_TOTAL  = "file_idt"
    val F_TANGGAL  = "file_tanggal"
    val F_URL  = "file_url"
    val F_PEMBAYARAN = "file_status"
    val F_ID_DOC = "id"
    val list  = arrayList
    var uri = Uri.EMPTY

    inner class ViewHolder(){
        var tsIDT : TextView? = null
        var tsNama : TextView? = null
        var tsMlebu : ConstraintLayout? = null
        var tsTotal : TextView? = null
        var tsTanggal : TextView? = null
        var tsPembayaran : TextView? = null

    }
    override fun getView(postion: Int, convertView: View?, parent: ViewGroup?): View {
        var holder = ViewHolder()
        var view: View? = convertView
        val e = postion + 1
        if(convertView == null ){
            var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_pesananadmin,null,true)
//            holder.tsNama = view!!.findViewById(R.id.tsNama) as TextView
            holder.tsTanggal = view!!.findViewById(R.id.tsTanggal) as TextView
            holder.tsIDT = view!!.findViewById(R.id.tsTotal) as TextView
            holder.tsPembayaran = view!!.findViewById(R.id.tsNama) as TextView
            holder.tsMlebu = view!!.findViewById(R.id.CLPesanan) as ConstraintLayout
            view.tag = holder
        }else{
            holder = view!!.tag as AdapterPesanan.ViewHolder
        }
        uri = Uri.parse(list.get(postion).get(F_URL).toString())
        holder.tsTanggal!!.setText(list.get(postion).get(F_TANGGAL).toString())
        holder.tsIDT!!.setText(list.get(postion).get(F_TOTAL).toString())
        holder.tsPembayaran!!.setText(list.get(postion).get(F_PEMBAYARAN).toString())
        holder.tsMlebu!!.setOnClickListener {
            val i = Intent(context, detail_transaksi_admin::class.java)
            i.putExtra("doc", list.get(postion).get(F_TOTAL).toString())
            context.startActivity(i)
        }
        return view!!
    }

    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }
}