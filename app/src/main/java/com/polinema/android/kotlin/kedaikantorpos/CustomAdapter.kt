package com.polinema.android.kotlin.kedaikantorpos

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class   CustomAdapter(val context : Context,
                    arrayList: ArrayList<HashMap<String,Any>>) : BaseAdapter() {

    val F_NAME  = "file_name"
    val F_HARGA  = "file_harga"
    val F_DESKRIPSI  = "file_deskripsi"
    val F_TYPE  = "file_type"
    val F_URL  = "file_url"
    val list  = arrayList
    var uri = Uri.EMPTY

    inner class ViewHolder(){
        var txFileName : TextView? = null
        var txFIleHarga : TextView? = null
        var txDeskripsi : TextView? = null
        var imv : ImageView? = null
    }
    override fun getView(postion: Int, convertView: View?, parent : ViewGroup?): View {
        var holder = ViewHolder()
        var view:View? = convertView
        if(convertView == null ){
            var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.row_menu,null,true)

            holder.txFileName = view!!.findViewById(R.id.txMenu) as TextView
            holder.txFIleHarga = view!!.findViewById(R.id.harganyo) as TextView
            holder.txDeskripsi = view!!.findViewById(R.id.txDeskripsi) as TextView

            holder.imv = view!!.findViewById(R.id.Imv) as ImageView

            view.tag = holder
        }else{
            holder = view!!.tag as ViewHolder
        }

        var fileType = list.get(postion).get(F_TYPE).toString()
        uri = Uri.parse(list.get(postion).get(F_URL).toString())

        holder.txFileName!!.setText(list.get(postion).get(F_NAME).toString())
        holder.txFIleHarga!!.setText(list.get(postion).get(F_HARGA).toString())
        holder.txDeskripsi!!.setText(list.get(postion).get(F_DESKRIPSI).toString())


        Picasso.get().load(uri).into(holder.imv)

        return view!!
    }

    override fun getItem(position : Int): Any {
        return list.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }
}