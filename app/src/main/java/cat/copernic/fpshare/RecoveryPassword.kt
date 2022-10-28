package cat.copernic.fpshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RecoveryPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_password)
    }
}