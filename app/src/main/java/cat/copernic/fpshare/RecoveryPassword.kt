package cat.copernic.fpshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RecoveryPassword : AppCompatActivity() {
    private lateinit var bontonRecoveryPassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_password)

        bontonRecoveryPassword = findViewById(R.id.button_singin_recoverypassword)

        bontonRecoveryPassword.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

    }
}