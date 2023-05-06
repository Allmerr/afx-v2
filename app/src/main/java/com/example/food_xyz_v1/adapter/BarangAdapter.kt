package com.example.food_xyz_v1.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.food_xyz_v1.R
import com.example.food_xyz_v1.model.Barang


class BarangAdapter(private val view: View, private val context: Context, private val dataset : List<Barang>) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>(){

    private var filteredDataset: List<Barang> = dataset

    fun filterDataset(query: String) {
        filteredDataset = if(query.isEmpty()) dataset else {
            dataset.filter { barang ->
                barang.namaBarang.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    class BarangViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val tvNamaItemBarang = view.findViewById<TextView>(R.id.tv_nama_item_barang)
        val tvHargaItemBarang = view.findViewById<TextView>(R.id.tv_harga_item_barang)

        val btnPlusItemBarang = view.findViewById<Button>(R.id.btn_plus_item_barang)
        val tvJumlahItemDibeli = view.findViewById<TextView>(R.id.tv_jumlah_item_dibeli_item_barang)
        val btnMinusItemBarang = view.findViewById<Button>(R.id.btn_minus_item_barang)

        val ivAddToCart = view.findViewById<ImageView>(R.id.iv_add_to_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val barangViewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return BarangViewHolder(barangViewHolder)
    }

    override fun getItemCount(): Int {
        return filteredDataset.size
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = filteredDataset[position]

        holder.tvNamaItemBarang.text = barang.namaBarang
        holder.tvHargaItemBarang.text = barang.hargaSatuan

        var i = 0

        holder.btnPlusItemBarang.setOnClickListener {
            i += 1

            holder.tvJumlahItemDibeli.text = i.toString()
            dataset[position].jumlahBarangDibeli = i

            selectedItems[dataset[position]] = i
        }

        holder.btnMinusItemBarang.setOnClickListener {
            i -= 1
            if(i <= 0){
                i = 0
                selectedItems[dataset[position]] = 0
            }else{
                dataset[position].jumlahBarangDibeli = i

                selectedItems[dataset[position]] = i
            }

            holder.tvJumlahItemDibeli.text = i.toString()
        }

        holder.ivAddToCart.setOnClickListener {
            getSelected()
            view.findViewById<TextView>(R.id.tv_total_harga).text = "Total Rp. ${totalPrice().toString()}"
        }
    }

    public var selectedItems = mutableMapOf<Barang,Int>()

    public fun getSelected() = selectedItems.filterValues { it>0 }

    public fun totalPrice() = selectedItems.map { it.key.hargaSatuan.toInt() * it.value }.sum()


}