package com.example.food_xyz_v1

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.food_xyz_v1.model.Barang

class InvoiceActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invoice_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val jumlahBarangDibeli = intent?.getStringExtra("jumlah_barang_dibeli").toString()
//
//        Toast.makeText(this, "test $jumlahBarangDibeli", Toast.LENGTH_LONG).show()

//        val selectedItems: Map<Barang, Int> = intent.getSerializableExtra("selected_items") as HashMap<Barang, Int>

//        val selectedItems = intent.extras?.getSerializable("selected_items") as? Map<Barang, Int>
//
//        Log.d("TESTAPP INVOICE", selectedItems.toString())
//
//        if(intent.extras == null){
//
//        }else{
//            val selectedItems = intent.getParcelableExtra<HashMap<Barang, Int>>("selected_items")
//        }
//        Log.d("TESTAPP INVOICE", selectedItems.toString())
//



    }

}