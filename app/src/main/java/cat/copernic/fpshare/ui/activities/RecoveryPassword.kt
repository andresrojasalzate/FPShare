package cat.copernic.fpshare.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ActivityRecoveryPasswordBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoveryPassword : AppCompatActivity() {

    private lateinit var emailRecovery: EditText
    private lateinit var buttonRecovery: Button
    private lateinit var buttonBack: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRecoveryPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityRecoveryPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init() // Inicia los elementos en pantalla

        listeners() // Escucha de los botones
    }

    private fun init() {
        emailRecovery = findViewById(R.id.editText_recovery)
        buttonBack = findViewById(R.id.btnBack)
        buttonRecovery = findViewById(R.id.btn_recovery)
        auth = Firebase.auth
    }

    private fun listeners() {
        /**
         * Si pulsamos el botón, nos enviará un correo de recuperación a través del
         * correo escrito por el usuario en el campo emailRecovery, si el correo proporcionado
         * no coincide con uno registrado saltará a la función error
         */
        buttonRecovery.setOnClickListener {
            val correoRecovery = emailRecovery.text.toString()

            if (camposVacios(correoRecovery)) {
                recuperarPassword(correoRecovery)
            } else {
                error()
            }
        }

        /**
         * Botón para volver al login
         */
        buttonBack.setOnClickListener {
            back()
        }
    }

    /**
     * Función para enviar el correo de recuperación de la contraseña de la cuenta del usuario
     */
    private fun recuperarPassword(email: String) {
        auth.setLanguageCode("es")
        auth.sendPasswordResetEmail(email).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                success()
            } else {
                error()
            }
        }
    }

    /**
     * Comprobación de que el campo de correo no esta vacío o en blanco
     */
    private fun camposVacios(correo: String): Boolean {
        return correo.isNotEmpty() && correo.isNotBlank()
    }

    /**
     * Función con un SnackBar avisando de que el correo no es valido
     */
    private fun error() {
        Snackbar.make(
            findViewById(R.id.passwordLayout),
            getString(R.string.errorEmail), BaseTransientBottomBar.LENGTH_SHORT
        ).show()
    }

    /**
     * Función para si hemos completado correctamente el envío del correo, nos envíe de nuevo
     * a la pantalla de inicio de sesión
     */
    private fun success() {
        Snackbar.make(
            findViewById(R.id.passwordLayout),
            getString(R.string.emailEnviadoRecu), BaseTransientBottomBar.LENGTH_SHORT
        ).show()
        startActivity(Intent(this, Login::class.java))
        finish()
    }

    /**
     * Función para volver atrás al login desde el botón de Volver
     */
    private fun back() {
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}