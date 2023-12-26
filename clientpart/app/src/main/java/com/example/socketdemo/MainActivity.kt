package com.example.socketdemo

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val serverHost = "http://192.168.108.1:5000"
//        socketClient = SocketClient(Profile.baseUrl).apply { start() }

//        val buttonSend = findViewById<Button>(R.id.send_button)
//        val editTextMessage = findViewById<EditText>(R.id.text_send)
//
//        buttonSend.setOnClickListener {
//            val message = editTextMessage.text.toString()
//            socketClient.sendMessage(message)
//            val connectInfo=findViewById<TextView>(R.id.connectInfo)
//            runOnUiThread{
//                connectInfo.text="send successfully"
//            }
//        }
        val startButton=findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{

            val usernameInput=findViewById<EditText>(R.id.username_input)
            val username=usernameInput.text
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