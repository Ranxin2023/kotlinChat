package com.example.socketdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException
import io.socket.emitter.Emitter;
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.IOException
import java.time.LocalTime

class MainActivity : AppCompatActivity() {
//    private lateinit var socketClient: SocketClient
    private lateinit var profilePhoto:ImageView
    private fun parseSIDCookie(cookiesHeader: String): String? {
        val cookies = cookiesHeader.split(";").map { it.trim() }
        for (cookie in cookies) {
            val parts = cookie.split("=")
            if (parts.size == 2 && parts[0] == "sid") {
                return parts[1]
            }
        }
        return null
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode == RESULT_OK && data!=null){
            val bundle:Bundle?=data.extras
            val finalPhoto: Bitmap = bundle?.get("data") as Bitmap
            if(finalPhoto!=null){
                this.profilePhoto.setImageBitmap(finalPhoto)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //--------------------------------take the photo start--------------------------------
        this.profilePhoto=findViewById<ImageView>(R.id.profile_photo_taken)
        if(ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CAMERA)!=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(
                Manifest.permission.CAMERA
            ), 100)
        }
        val photoButton=findViewById<Button>(R.id.take_photo_button)
        photoButton.setOnClickListener {
            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 100)
        }
        //--------------------------------take the photo end--------------------------------
        val startButton=findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{

            val usernameInput=findViewById<EditText>(R.id.username_input)
            val username=usernameInput.text.toString()
            val jsonArr=JSONArray(arrayOf(username, LocalTime.now()))
            val jsonObject=JSONObject()
            jsonObject.put("method", "login")
            jsonObject.put("args", jsonArr)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody=jsonObject.toString().toRequestBody(mediaType)
            Thread{
                val request= Request.Builder()
                    .url(Profile.baseUrl)
                    .post(requestBody)
                    .build()

                val response: Response
                val client= OkHttpClient()
                val call=client.newCall(request)
                try{
                    response=call.execute()
                    if(response.isSuccessful){
                        val serverResponse: String? = response.body?.string()

                        val jsonObjectFromServer = JSONObject(serverResponse?: "")
                        val success = jsonObjectFromServer.getString("success").toBoolean()
                        val failureInformation=jsonObjectFromServer.getString("error msg").toString()
                        if(success){
//                            store sid into profile
                            val cookiesHeader = response.header("Set-Cookie")
                            if (cookiesHeader != null) {
                                val sidCookie = parseSIDCookie(cookiesHeader)
                                if(sidCookie!=null&&Profile.sid!=sidCookie){
                                    Profile.sid=sidCookie
                                }
                            }
                            val nickname=jsonObjectFromServer.getString("nickname").toString()
                            Profile.nickname=nickname
//                            runOnUiThread {
//                                val failureText=findViewById<TextView>(R.id.failure_text)
//                                failureText.text= nickname
//                            }
                            val intent= Intent(this, MessageActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            runOnUiThread {
                                val failureText=findViewById<TextView>(R.id.failure_text)
                                failureText.text=failureInformation
                            }
                        }
                    }
                    else{
                        println("Request failed. Status code: ${response.code}")
                    }
                }
                catch (e: IOException){
                    e.printStackTrace()
                }
            }.start()
        }
    }
}