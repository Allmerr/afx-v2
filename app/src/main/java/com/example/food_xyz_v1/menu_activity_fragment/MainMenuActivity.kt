package com.example.food_xyz_v1.menu_activity_fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food_xyz_v1.DaftarActivity
import com.example.food_xyz_v1.InvoiceActivity
import com.example.food_xyz_v1.R
import com.example.food_xyz_v1.adapter.BarangAdapter
import com.example.food_xyz_v1.env.BaseUrl
import com.example.food_xyz_v1.model.Barang
import com.example.food_xyz_v1.utils.VolleyUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainMenuActivity : Fragment(R.layout.fragment_main_menu_activity)  {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val idUser = bundle?.getString("id_user").toString()

        GlobalScope.launch {
            initRvBarang(view, idUser)
        }
    }

    public suspend fun makeRequest(context: Context, url : String, onSuccess: () -> Unit, onFail: () -> Unit){
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, {response ->
            onSuccess()
        },{
            onFail()
            Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
        })
        queue.add(jsonObjectRequest)
    }

    private suspend fun initRvBarang(view: View,idUser :String) {
        val baseUrl = BaseUrl().BASEURL
        val url = "$baseUrl/Barang?search="
        val queue = Volley.newRequestQueue(view.context)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,{response->
            val arrayData = response.getJSONArray("data")

            var dataset = mutableListOf<Barang>()

            for (i in 0 until arrayData.length()){
                var barang =  arrayData.getJSONObject(i)

                var idBarang = barang["Id_Barang"].toString()
                var namaBarang = barang["Nama_Barang"].toString()
                var jumlahBarang = barang["Jumlah_Barang"].toString()
                var hargaSatuan =  barang["Harga_Satuan"].toString()

                dataset.add(Barang(idBarang,namaBarang,jumlahBarang,hargaSatuan,0))

            }

            var barangAdapter = BarangAdapter(view, view.context, dataset)


            view.findViewById<RecyclerView>(R.id.rv_barang).adapter = barangAdapter

            view.findViewById<EditText>(R.id.et_seacrh_barang).addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.let { barangAdapter.filterDataset(it.toString()) }
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            view.findViewById<Button>(R.id.btn_bayar_main_menu).setOnClickListener {
                if(barangAdapter.getSelected().isEmpty()){
                    Toast.makeText(view.context,"Silahkan pilih barang terlebih dahulu",Toast.LENGTH_LONG).show()
                }else{
                    barangAdapter.getSelected().forEach{
                        //Toast.makeText(view.context,"${it.key.namaBarang} stok = ${it.key.jumlahBarang} dibeli = ${it.value}",Toast.LENGTH_LONG).show()
                        if(it.key.jumlahBarang.toInt() >= it.value){
                            val urlTransaksi = "$baseUrl/Transaksi?totalBarangDibeli=${it.value}&totalBayar=${it.key.hargaSatuan.toInt() * it.value}&idUser=${idUser}&idBarang=${it.key.idBarang}"
                            GlobalScope.launch {
                                makeRequest(view.context,urlTransaksi,{
                                    Toast.makeText(view.context,"${it.key.namaBarang} berhasil dibeli",Toast.LENGTH_LONG).show()
                                },{
                                    Toast.makeText(view.context,"${it.key.namaBarang} gagal dibeli",Toast.LENGTH_LONG).show()
                                })
                            }
                        }else{
                            Toast.makeText(view.context,"Stok ${it.key.namaBarang} kami kurang untuk melakukan transaksi",Toast.LENGTH_LONG).show()
                        }
                    }
                    val intent = Intent(view.context, InvoiceActivity::class.java)

//                    val selectedItems = barangAdapter.getSelected().toList()
//
//                    intent.putExtra("selected_items", selectedItems)
//
//                    startActivity(intent)
                }
            }

        },{
            Toast.makeText(view.context,it.toString(), Toast.LENGTH_LONG).show()

        })

        queue.add(jsonObjectRequest)
    }
}