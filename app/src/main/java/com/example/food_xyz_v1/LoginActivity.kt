package com.example.food_xyz_v1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food_xyz_v1.env.BaseUrl
import com.example.food_xyz_v1.utils.VolleyUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val baseUrl = BaseUrl().BASEURL

        findViewById<Button>(R.id.btn_login_login).setOnClickListener {
            val etUsername = findViewById<EditText>(R.id.et_username_login)
            val etPassword = findViewById<EditText>(R.id.et_password_login)

            if(etUsername.text.toString() != "" && etPassword.text.toString() != ""){
                val queue = Volley.newRequestQueue(this)
                val url = "$baseUrl/Login?username=${etUsername.text}&password=${etPassword.text}"
                val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, {response ->
                    if(response["status_code"].toString() == "200" && response["message"].toString() == "success"){
                        val dataUser = response.getJSONObject("data")

                        val intent = Intent(this,MenuActivity::class.java)
                        intent.putExtra("id_user", dataUser["Id_User"].toString())

                        Toast.makeText(this,"Berhasil Login", Toast.LENGTH_LONG).show()

                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"Username atau Password Salah", Toast.LENGTH_LONG).show()
                    }
                }, {
                    Toast.makeText(this, it.toString() , Toast.LENGTH_LONG).show()
                })
                queue.add(jsonObjectRequest)
            }else{
                Toast.makeText(this,"Data Diri Belum Lengkap", Toast.LENGTH_LONG).show()
            }
        }

        findViewById<Button>(R.id.btn_daftar_login).setOnClickListener {
            startActivity(Intent(this,DaftarActivity::class.java))
        }
    }

}

