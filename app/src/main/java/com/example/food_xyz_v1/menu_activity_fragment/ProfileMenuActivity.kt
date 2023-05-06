package com.example.food_xyz_v1.menu_activity_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food_xyz_v1.LoginActivity
import com.example.food_xyz_v1.R
import com.example.food_xyz_v1.env.BaseUrl
import com.google.android.material.card.MaterialCardView

class ProfileMenuActivity : Fragment(R.layout.fragment_profile_menu_activity) {
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

        doProfileCreate(view, idUser)

        view.findViewById<MaterialCardView>(R.id.mcv_logout_profile).setOnClickListener {
            val intent = Intent(view.context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    private fun doProfileCreate(view: View, idUser: String) {
        val baseUrl = BaseUrl().BASEURL

        val queue = Volley.newRequestQueue(view.context)
        val url = "$baseUrl/User?id_user=${idUser}"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, {response ->
            if(response["status_code"].toString() == "200" && response["message"].toString() == "success"){
                val dataUser = response.getJSONObject("data")
                view.findViewById<TextView>(R.id.tv_nama_profile).text = dataUser["Nama"].toString()
                view.findViewById<TextView>(R.id.tv_alamat_profile).text = dataUser["Alamat"].toString()
                view.findViewById<TextView>(R.id.tv_telpon_profile).text = dataUser["Telpon"].toString()
            }else{
                Toast.makeText(view.context,"${response.toString()}", Toast.LENGTH_LONG).show()
            }
        }, {
            Toast.makeText(view.context,"Something went wrong", Toast.LENGTH_LONG).show()
        })
        queue.add(jsonObjectRequest)
    }
}