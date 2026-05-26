package com.a2.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.a2.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class A2FcmService : FirebaseMessagingService() {

    @Inject lateinit var firestore: FirebaseFirestore
    @Inject lateinit var auth: FirebaseAuth

    override fun onNewToken(token: String) {
        val uid = auth.currentUser?.uid ?: return
        CoroutineScope(Dispatchers.IO).launch {
            firestore.collection("usuarios").document(uid).update("fcmToken", token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        crearCanales()
        val tipo = message.data["tipo"] ?: return
        val notif = when (tipo) {
            "NUEVO_ENVIO"          -> buildEnvioNotif(message)
            "ENVIO_ACTUALIZADO"    -> buildEnvioNotif(message)
            "ALERTA"               -> buildAlertaNotif(message)
            "MENSAJE"              -> buildMensajeNotif(message)
            else                   -> return
        }
        NotificationManagerCompat.from(this)
            .notify(System.currentTimeMillis().toInt(), notif)
    }

    private fun crearCanales() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        listOf(
            NotificationChannel("envios",      "Envíos",       NotificationManager.IMPORTANCE_HIGH),
            NotificationChannel("alertas",     "Alertas",      NotificationManager.IMPORTANCE_HIGH),
            NotificationChannel("mensajes",    "Mensajes",     NotificationManager.IMPORTANCE_HIGH)
        ).forEach { manager.createNotificationChannel(it) }
    }

    private fun mainIntent() = PendingIntent.getActivity(
        this, 0,
        Intent(this, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP },
        PendingIntent.FLAG_IMMUTABLE
    )

    private fun envioIntent(envioId: String?) = PendingIntent.getActivity(
        this, System.currentTimeMillis().toInt(),
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("envioId", envioId)
        },
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    private fun buildEnvioNotif(msg: RemoteMessage) =
        NotificationCompat.Builder(this, "envios")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(msg.data["titulo"] ?: "Actualización de envío")
            .setContentText(msg.data["mensaje"] ?: "Tu envío ha sido actualizado")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(envioIntent(msg.data["envioId"]))
            .setAutoCancel(true).build()

    private fun buildAlertaNotif(msg: RemoteMessage) =
        NotificationCompat.Builder(this, "alertas")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("⚠️ ${msg.data["titulo"] ?: "Alerta"}")
            .setContentText(msg.data["mensaje"] ?: "")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(mainIntent())
            .setAutoCancel(true).build()

    private fun buildMensajeNotif(msg: RemoteMessage) =
        NotificationCompat.Builder(this, "mensajes")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(msg.data["remitente"] ?: "Mensaje nuevo")
            .setContentText(msg.data["texto"] ?: "")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(mainIntent())
            .setAutoCancel(true).build()
}
