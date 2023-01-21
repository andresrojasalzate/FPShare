package cat.copernic.fpshare.ui.activities

import android.app.NotificationChannel
import android.app.NotificationManager
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
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

/**
 * Activity de pantalla de login de usuario
 *
 * @author FPShare
 */
class Login : AppCompatActivity() {

    private lateinit var correoLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var bontonLogin: Button
    private lateinit var bontonRegistre: Button
    private lateinit var textViewForgotPassword: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: LoginBinding
    private var bd = FirebaseFirestore.getInstance()

    private var splashScreenMS: Long = 1000
    companion object {
         var vecesIniciado = 0
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
        bd.collection("Usuarios").whereEqualTo("email", correo).get().addOnSuccessListener {
            if (!it.isEmpty){
                auth.signInWithEmailAndPassword(correo, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }else{
                            error()
                        }
                    }
            }else {
                Snackbar.make(
                    findViewById(R.id.loginLayout),
                    getString(R.string.user_not_registred), BaseTransientBottomBar.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun error() {
        Snackbar.make(
            findViewById(R.id.loginLayout),
            getString(R.string.errorUserEquivocado), BaseTransientBottomBar.LENGTH_SHORT
        ).show()
    }

    override fun onStart() {
        super.onStart()
        createNotificationChannel()
        contadorIniciosApp()
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    /**
     * Creamos un cnal de notificacion si el sistema operativo es mayor a Android Oreo paa poder enviar notificaciones
     */
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

    /**
     * Esta función la utilizamos para saber cuantas veces se ha abierto la app
     */
    private fun contadorIniciosApp(){
        //obtenemos las preferencias compartidas "app_data" en modo privado
        val prefs = getSharedPreferences("app_data", Context.MODE_PRIVATE)

        // obtenemos el contador de inicios de la aplicación desde las preferencias compartidas si no existe lo creamos
        val startCount = prefs.getInt("start_count", 0) + 1
        //creamos un editor para editar las prefencias compartidas
        val editor = prefs.edit()
        //esblecemos el nuevo valor del contador
        editor.putInt("start_count", startCount)
        //aplicamos lo cambios
        editor.apply()
        // y asignamos la veces que se ha iniciado a una variable que se comprte con un compaion object
        vecesIniciado = startCount
        }

    }


