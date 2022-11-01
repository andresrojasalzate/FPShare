package cat.copernic.fpshare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RecoveryPassword : AppCompatActivity() {

    private lateinit var emailRecovery: EditText
    private lateinit var buttonRecovery: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_password)

        init()

        listeners()
    }

    private fun init() {
        emailRecovery = findViewById(R.id.editText_recovery)
        buttonRecovery = findViewById(R.id.button_signin_recoverypassword)
    }

    private fun listeners() {
        buttonRecovery.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}