package com.example.socketdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.time.LocalTime

class MainActivity : AppCompatActivity() {
//    private lateinit var socketClient: SocketClient
    private val PICK_IMAGE = 100
    private lateinit var profilePhoto:ImageView

    private fun imageViewToBase64(imageView: ImageView): String {
        // Get the bitmap from ImageView
        imageView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(imageView.drawingCache)
        imageView.isDrawingCacheEnabled = false

        // Convert bitmap to ByteArrayOutputStream
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byteArray = outputStream.toByteArray()

        // Encode byteArray to Base64 string
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

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

    private fun takePhoto(){
        this.profilePhoto=findViewById<ImageView>(R.id.profile_photo_taken)
        if(ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CAMERA)!=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(
                Manifest.permission.CAMERA
            ), PICK_IMAGE)
        }
        val photoButton=findViewById<Button>(R.id.take_photo_button)
        photoButton.setOnClickListener {
            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, PICK_IMAGE)
        }
    }

    private fun uploadUser(){
        val startButton=findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{
            val base64Image=this.imageViewToBase64(this.profilePhoto)
            val usernameInput=findViewById<EditText>(R.id.username_input)
            val username=usernameInput.text.toString()
            val jsonArr=JSONArray(arrayOf(username, LocalTime.now(), base64Image))
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
//                            runOnUiThread{
//                                Toast.makeText(applicationContext, "length of profile.sid is${Profile.sid.length}", Toast.LENGTH_SHORT)
//                            }
                            val nickname=jsonObjectFromServer.getString("nickname").toString()
                            val photoCode=jsonObjectFromServer.getString("profile photo").toString()
                            Profile.nickname=nickname
                            Profile.photoId=photoCode
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
                        Toast.makeText(applicationContext, "Request failed. Status code: ${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e: IOException){
                    e.printStackTrace()
                }
            }.start()
        }
    }

//    private fun uploadPhoto(){
//        val uploadButton=findViewById<Button>(R.id.upload_photo_button)
//        uploadButton.setOnClickListener {
////            runOnUiThread{
////                Toast.makeText(applicationContext, "Start upload", Toast.LENGTH_SHORT).show()
////            }
//            val profilePhotoBitmap = (this.profilePhoto.drawable as BitmapDrawable).bitmap
//            // Convert bitmap to byte array
//            val byteArrayOutputStream = ByteArrayOutputStream()
//            profilePhotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//            val byteArray = byteArrayOutputStream.toByteArray()
//            // Create request body
//            val MEDIA_TYPE_JPEG = "image/jpeg".toMediaTypeOrNull()
//            val requestBody = MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("file", "image.jpg", RequestBody.create(MEDIA_TYPE_JPEG, byteArray))
//                .build()
//
//
//            Thread{
//                // Create request
//                val request = Request.Builder()
//                    .url(Profile.baseUrl)
//                    .post(requestBody)
//                    .build()
//                val response: Response
//                val client= OkHttpClient()
//                val call=client.newCall(request)
//                try{
////                    runOnUiThread {
////                        Toast.makeText(applicationContext, "Start response", Toast.LENGTH_SHORT).show()
////                    }
//                    response=call.execute()
//                    if(response.isSuccessful){
////                        execute the code
//                        val serverResponse: String? = response.body?.string()
//
//                        val jsonObjectFromServer = JSONObject(serverResponse?: "")
//                        val success = jsonObjectFromServer.getString("success").toBoolean()
//                        val failureInformation=jsonObjectFromServer.getString("error msg").toString()
//                        if(success){
//                            val photoId=jsonObjectFromServer.getString("photo id").toString()
//                            Profile.photoId=photoId
//                            runOnUiThread {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Photo Id is: ${Profile.photoId}",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                        else{
//                            runOnUiThread{
//                                Toast.makeText(applicationContext, "error message: $failureInformation", Toast.LENGTH_SHORT).show()
//
//                            }
//                        }
//                    }
//                    else{
//                        println("Request failed. Status code: ${response.code}")
//                    }
//                }
//                catch (e: IOException){
//                    e.printStackTrace()
//                }
//            }.start()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //--------------------------------take the photo start--------------------------------
        this.takePhoto()
        //--------------------------------take the photo end--------------------------------
        //--------------------------------upload username start--------------------------------
        this.uploadUser()
        //--------------------------------upload username start--------------------------------
        //--------------------------------upload photo start--------------------------------
//        this.uploadPhoto()
        //--------------------------------upload photo end--------------------------------
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE && resultCode == RESULT_OK && data!=null){
            val bundle:Bundle?=data.extras
            val finalPhoto: Bitmap = bundle?.get("data") as Bitmap
            if(finalPhoto!=null){
                this.profilePhoto.setImageBitmap(finalPhoto)
            }
        }
    }
}