package cat.copernic.fpshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide

class SplashScreen : AppCompatActivity() {

    val DURACION: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val logo = findViewById<ImageView>(R.id.imgSplashScreen)

        Glide.with(this).load(R.drawable.logo_fpshare).into(logo)

        cambiarActivity()
    }

    private fun cambiarActivity() {
        Handler().postDelayed(Runnable {
            val intent =
                Intent(this, Login::class.java)
            startActivity(intent)
        }, DURACION)
    }
}