package com.example.toothpaste.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.toothpaste.MainActivity
import com.example.toothpaste.R
import com.example.toothpaste.notification.OreoNotification
import com.example.toothpaste.notification.TokenModel
import com.example.toothpaste.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFCMServices : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val sented = remoteMessage.data["sented"]
        val user = remoteMessage.data["user"]
        val to     =  remoteMessage.data["sendto"] // testing

        if (user!=null)
           Log.d("ddd", user!!)
        val preferences =
            getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val currentUser = preferences.getString("currentuser", "none")

        val firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser == null)
            Log.d("token","FirebaseUser is null")

        if (firebaseUser!=null && to=="/topics/events"){
            if (currentUser != user) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendOreoNotification(remoteMessage)
                    Log.d("token","The Notification sent")

                } else {
                    sendNotification(remoteMessage)
                    Log.d("token","The Notification sent")
                }
            }
        }

        else if (firebaseUser != null && sented == firebaseUser.uid) {
            if (currentUser != user) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendOreoNotification(remoteMessage)
                    Log.d("token","The Notification sent")

                } else {
                    sendNotification(remoteMessage)
                    Log.d("token","The Notification sent")
                }
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM",token)
        val firebaseUser = FirebaseAuth.getInstance().currentUser

//        val refreshToken = FirebaseInstanceId.getInstance().token
        if (firebaseUser != null) {
            FirebaseDatabase.getInstance().reference
                .child("Tokens")
                .child(FirebaseAuth.getInstance().uid!!)
                .setValue(TokenModel(FirebaseAuth.getInstance().uid!!, token))
                .addOnSuccessListener {
                    Constants.token=token
                }
        }
    }

    private fun sendOreoNotification(remoteMessage: RemoteMessage) {
        val user = remoteMessage.data["user"]
        val icon = remoteMessage.data["icon"]
        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]
        val notification = remoteMessage.notification
//        val j = (user!!.replace("[\\D]".toRegex(), "")).toLong()
//        val j = (user!!.replace("[\\D]".toRegex(), "")).toInt()
        val intent = Intent(this, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putString("userid", user)
        bundle.putBoolean("server",true)
        intent.putExtras(bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val oreoNotification = OreoNotification(this)
        val builder: Notification.Builder = oreoNotification.getOreoNotification(
            title, body, pendingIntent,
            defaultSound, icon
        )
//        var i = 0
        var i = 1
//        if (j > 0) {
//            i = j.toInt()
//        }
        oreoNotification.getManager().notify(i, builder.build())
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val user = remoteMessage.data["user"]
        val icon = remoteMessage.data["icon"]
        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]
        val notification = remoteMessage.notification
//        val j = user!!.replace("[\\D]".toRegex(), "").toInt()
//        val j = (user!!.replace("[\\D]".toRegex(), "")).toLong()
        val intent = Intent(this, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putString("userid", user)
        bundle.putBoolean("server",true)
        intent.putExtras(bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(this)
//            .setSmallIcon(icon!!.toInt())
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setContentIntent(pendingIntent)
        val noti =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        var i = 0
//        if (j > 0) {
//            i = j.toInt()
//        }
        noti.notify(1, builder.build())
    }
}