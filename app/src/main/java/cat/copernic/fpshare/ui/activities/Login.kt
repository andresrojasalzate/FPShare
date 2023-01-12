package cat.copernic.fpshare.ui.activities

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.LoginBinding
import cat.copernic.fpshare.modelo.AlarmReceiver
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class Login : AppCompatActivity() {

    private lateinit var correoLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var bontonLogin: Button
    private lateinit var bontonRegistre: Button
    private lateinit var textViewForgotPassword: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: LoginBinding

    private var splashScreenMS: Long = 1000

    companion object {
        val IDCanal = "FPShare"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(splashScreenMS)
        setTheme(R.style.Theme_Fpshare)
        this.supportActionBar!!.hide()

        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        inicializacion()

        bontonLogin.setOnClickListener {
            val correo = correoLogin.text.toString()
            val password = passwordLogin.text.toString()

            if (camposVacios(correo, password)) {
                login(correo, password)
            } else {
                error()
            }
        }
        bontonRegistre.setOnClickListener {

            startActivity(Intent(this, Register::class.java))
            finish()
        }
        textViewForgotPassword.setOnClickListener {
            startActivity(Intent(this, RecoveryPassword::class.java))
            finish()
        }

    }

    private fun inicializacion() {

        correoLogin = findViewById(R.id.edittext_email_login)
        passwordLogin = findViewById(R.id.edittext_password_login)
        bontonLogin = findViewById(R.id.button_singin_login)
        bontonRegistre = findViewById(R.id.button_register_login)
        textViewForgotPassword = findViewById(R.id.textView_forgotten_password)
        auth = Firebase.auth

    }

    private fun camposVacios(correo: String, password: String): Boolean {
        return correo.isNotEmpty() && password.isNotEmpty()
                && correo.isNotBlank() && password.isNotBlank()
    }

    private fun login(correo: String, password: String) {

        auth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    error()
                }
            }
    }

    private fun error() {
        Snackbar.make(
            findViewById(R.id.loginLayout),
            "Wrong user or password", BaseTransientBottomBar.LENGTH_SHORT
        ).show()
    }

    override fun onStart() {
        super.onStart()
        createNotificationChannel()
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    private fun createNotificationChannel() {
        //Creamos el Canal de notificacion pero solo apartir de android 8.0
        // porque en versiones anteriores no existe
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal de notificaciones de FPShare"
            val descriptionText = "Aqui se puede gestionar las notificaciones de FPShare"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val CHANNEL_ID = "1"
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Aqui registramos finalmente el canal
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
    private fun setAlarm() {
        val alarmIntent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent,
            PendingIntent.FLAG_IMMUTABLE)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, 20)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }


}