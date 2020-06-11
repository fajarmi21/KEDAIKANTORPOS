package com.polinema.android.kotlin.kedaikantorpos

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class AdapterTransaksi(val context : Context,
                       arrayList: ArrayList<HashMap<String,Any>>) : BaseAdapter() {
    val F_NAME  = "file_name"
    val F_TOTAL  = "file_total"
    val F_TANGGAL  = "file_tanggal"
    val F_URL  = "file_url"
    val F_PEMBAYARAN = "file_status"
    val list  = arrayList
    var uri = Uri.EMPTY

    inner class ViewHolder(){
        var tsIDT : TextView? = null
        var tsNama : TextView? = null
        var tsNomer : TextView? = null
        var tsTotal : TextView? = null
        var tsTanggal : TextView? = null
        var tsPembayaran : TextView? = null
        var cv : ConstraintLayout? = null

    }
    override fun getView(postion: Int, convertView: View?, parent: ViewGroup?): View {
        var holder = ViewHolder()
        var view: View? = convertView
        val e = postion + 1
        if(convertView == null ){
            var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_transaksi,null,true)
//            holder.tsNama = view!!.findViewById(R.id.tsNama) as TextView
            holder.tsTanggal = view!!.findViewById(R.id.tsTanggal) as TextView
            holder.tsTotal = view!!.findViewById(R.id.tsTotal) as TextView
            holder.tsPembayaran = view!!.findViewById(R.id.tsNama) as TextView
            holder.cv = view!!.findViewById(R.id.itemT) as ConstraintLayout
            view.tag = holder
        }else{
            holder = view!!.tag as AdapterTransaksi.ViewHolder
        }

        uri = Uri.parse(list.get(postion).get(F_URL).toString())
//        holder.tsNama!!.setText(list.get(postion).get(F_NAME).toString())
        holder.tsTanggal!!.setText(list.get(postion).get(F_TANGGAL).toString())
        holder.tsTotal!!.setText(list.get(postion).get(F_TOTAL).toString())
        holder.tsPembayaran!!.setText(list.get(postion).get(F_PEMBAYARAN).toString())
        holder.cv!!.setOnClickListener {
            Log.e("coba", list.get(postion).get("file_idt").toString())
            val i = Intent(context, detail_transaksi::class.java)
            i.putExtra("doc", list.get(postion).get("file_idt").toString())
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