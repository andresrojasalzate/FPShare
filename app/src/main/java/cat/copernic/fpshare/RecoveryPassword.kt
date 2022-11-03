package cat.copernic.fpshare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import cat.copernic.fpshare.databinding.ActivityRecoveryPasswordBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoveryPassword : AppCompatActivity() {

    private lateinit var emailRecovery: EditText
    private lateinit var buttonRecovery: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        val binding = ActivityRecoveryPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        listeners()
    }

    private fun init() {
        emailRecovery = findViewById(R.id.editText_recovery)
        buttonRecovery = findViewById(R.id.btn_recovery)
        auth = Firebase.auth
    }

    private fun listeners() {
        buttonRecovery.setOnClickListener {
            val correoRecovery = emailRecovery.text.toString()

            if (camposVacios(correoRecovery)) {
                recuperarPassword(correoRecovery)
            } else {
                error()
            }
        }
    }

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

    private fun camposVacios(correo: String): Boolean {
        return correo.isNotEmpty() && correo.isNotBlank()
    }

    private fun error() {
        Snackbar.make(
            findViewById(R.id.passwordLayout),
            "Wrong email", BaseTransientBottomBar.LENGTH_SHORT
        ).show()
    }

    private fun success() {
        Snackbar.make(
            findViewById(R.id.passwordLayout),
            "Email sent correctly", BaseTransientBottomBar.LENGTH_SHORT
        ).show()
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}