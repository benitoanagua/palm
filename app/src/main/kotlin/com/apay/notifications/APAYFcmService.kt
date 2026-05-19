package com.apay.notifications

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
import com.apay.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class APAYFcmService : FirebaseMessagingService() {

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
            "NUEVA_TASA"             -> buildTasaNotif(message)
            "COMPROBANTE_VERIFICADO" -> buildComprobanteNotif(message, "✅ Comprobante verificado")
            "COMPROBANTE_RECHAZADO"  -> buildComprobanteNotif(message, "❌ Comprobante rechazado")
            "ALERTA"                 -> buildAlertaNotif(message)
            "NUEVO_MENSAJE"          -> buildMensajeNotif(message)
            else                     -> return
        }
        NotificationManagerCompat.from(this)
            .notify(System.currentTimeMillis().toInt(), notif)
    }

    private fun crearCanales() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        listOf(
            NotificationChannel("tasas",        "Nueva tasa",    NotificationManager.IMPORTANCE_HIGH),
            NotificationChannel("comprobantes", "Comprobantes",  NotificationManager.IMPORTANCE_DEFAULT),
            NotificationChannel("alertas",      "Alertas",       NotificationManager.IMPORTANCE_HIGH),
            NotificationChannel("mensajes",     "Mensajes",      NotificationManager.IMPORTANCE_HIGH)
        ).forEach { manager.createNotificationChannel(it) }
    }

    private fun mainIntent() = PendingIntent.getActivity(
        this, 0,
        Intent(this, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP },
        PendingIntent.FLAG_IMMUTABLE
    )

    private fun chatIntent(chatId: String?, titulo: String?) = PendingIntent.getActivity(
        this, System.currentTimeMillis().toInt(),
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("chatId", chatId)
            putExtra("titulo", titulo)
        },
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    private fun buildTasaNotif(msg: RemoteMessage) =
        NotificationCompat.Builder(this, "tasas")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Nueva tasa publicada")
            .setContentText(msg.data["mensaje"] ?: "Tasa actualizada")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(mainIntent())
            .setAutoCancel(true).build()

    private fun buildComprobanteNotif(msg: RemoteMessage, titulo: String) =
        NotificationCompat.Builder(this, "comprobantes")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(titulo)
            .setContentText(msg.data["mensaje"] ?: "Tu comprobante fue procesado")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(mainIntent())
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
            .setContentIntent(chatIntent(msg.data["chatId"], msg.data["remitente"]))
            .setAutoCancel(true).build()
}
