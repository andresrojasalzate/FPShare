package cat.copernic.fpshare.modelo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import cat.copernic.fpshare.R
import cat.copernic.fpshare.ui.activities.MainActivity

/**
 * Clase AlarmReciber que se utiliza cuando una alarma se activa
 */
class AlarmReceiver: BroadcastReceiver() {

    /**
     * Función onReceive que creará  una notificación  y la enviara
     * @param context de tipo Context
     * @param intent fde tipo Intent
     */
    override fun onReceive(context: Context?, intent: Intent?) {

        //creamos un intent a la mainactivity
        val i = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        //creamos un pandingintent
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE)

        //establacemos los atributos de la notificación
        val textTitle = context?.getString(R.string.fpshare_noti)
        val textContent = context?.getString(R.string.contenido_notificación)
        val CHANNEL_ID = "1"

        //creamos la notificación y asigamos los atributos
        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_fpshare)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        //creamos objeto NotificationManager
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Muestra la notificación
        notificationManager.notify(0, builder.build())
        MainActivity.alarma = 0

        }

}

