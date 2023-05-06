package com.example.food_xyz_v1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food_xyz_v1.env.BaseUrl

class DaftarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daftar_activity)

        val baseUrl = BaseUrl().BASEURL

        findViewById<Button>(R.id.btn_daftar_daftar).setOnClickListener {
            val etNamaLengkap = findViewById<EditText>(R.id.et_nama_daftar).text.toString()
            val etUsername = findViewById<EditText>(R.id.et_username_daftar).text.toString()
            val etAlamat = findViewById<EditText>(R.id.et_alamat_daftar).text.toString()
            val etTelpon = findViewById<EditText>(R.id.et_telpon_daftar).text.toString()
            val etPassword = findViewById<EditText>(R.id.et_password_daftar).text.toString()
            val etKonfirmasiPassword = findViewById<EditText>(R.id.et_konfirmasi_password_daftar).text.toString()

            val verifikasiData = etNamaLengkap != "" && etUsername != ""  && etAlamat != ""   && etTelpon != ""  && etPassword != ""   && etKonfirmasiPassword != "" && (etPassword == etKonfirmasiPassword)
            if(verifikasiData){
                val queue = Volley.newRequestQueue(this)
                val url = "$baseUrl/Register?nama=${etNamaLengkap}&alamat=${etAlamat}&telpon=a${etTelpon}&username=${etUsername}&password=${etPassword}"
                val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
                    if(response["status_code"].toString() == "200" && response["message"].toString() == "success"){
                        Toast.makeText(this,"Berhasil Membuat Akun", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,LoginActivity::class.java))
                    }else{
                        Toast.makeText(this,"Username Sudah Dipakai Salah", Toast.LENGTH_LONG).show()
                    }
                }, {
                    Toast.makeText(this, it.toString() , Toast.LENGTH_LONG).show()
                })
                queue.add(jsonObjectRequest)
            }else{
                Toast.makeText(this,"Data Diri Belum Lengkap $etPassword == $etKonfirmasiPassword = ${etPassword == etKonfirmasiPassword}", Toast.LENGTH_LONG).show()
            }
        }

        findViewById<Button>(R.id.btn_login_daftar).setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }
}