package com.example.food_xyz_v1.utils

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class VolleyUtils {
    public suspend fun makeRequest(context: Context, url : String, onSuccess : (response: JSONObject?) -> Unit, onFail : (error : VolleyError ) -> Unit){
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, {response ->
            onSuccess(response)
        },{error ->
            onFail(error)
        })
        queue.add(jsonObjectRequest)
    }
}