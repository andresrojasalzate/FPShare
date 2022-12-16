package cat.copernic.fpshare.modelo

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.from
import cat.copernic.fpshare.R
import cat.copernic.fpshare.ui.activities.MainActivity

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val i = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, i, 0)

        //establacemos los atributos de la notificación
        val textTitle = "Notificación de FPShare"
        val textContent = "Contenido de la notificación"
        val CHANNEL_ID = "1"

        //creamos la notificación y asigamos los atributos
        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_fpshare)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        //enviamos la notificación
        with(NotificationManagerCompat.from(context)) {

            val notificationId = 0
            notify(notificationId, builder.build())
        }
        }
    }
}