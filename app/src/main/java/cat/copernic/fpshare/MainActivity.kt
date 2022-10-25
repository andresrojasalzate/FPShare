package cat.copernic.fpshare

import android.os.Bundle
import cat.copernic.fpshare.R
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // TODO Borrar cuando el login funcione
        Thread.sleep(2000)

        setTheme(R.style.Theme_Fpshare)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Hacemos algun cambio
    }
}