package cat.copernic.fpshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var correoLogin:EditText
    private lateinit var passwordLogin:EditText
    private lateinit var bontonLogin: Button
    private lateinit var bontonRegistre:Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        // TODO Descomentar cuando funcione el login
        // Thread.sleep(2000)

        // setTheme(R.style.Theme_Fpshare)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        inicializacion()

        bontonLogin.setOnClickListener {
            val correo = correoLogin.text.toString()
            val password = passwordLogin.text.toString()

            if(camposVacios(correo, password)){
                login(correo, password)
            } else{
                Toast.makeText(applicationContext,"Faltan campos por completar", Toast.LENGTH_LONG).show()
            }
        }
        bontonRegistre.setOnClickListener {

            startActivity(Intent(this,Register::class.java))
            finish()
        }
    }
    private fun inicializacion(){

        correoLogin = findViewById(R.id.imput_email_login)
        passwordLogin = findViewById(R.id.imput_password_login)
        bontonLogin = findViewById(R.id.button_singin_login)
        bontonRegistre = findViewById(R.id.button_register_login)
        auth= Firebase.auth

    }
    private fun camposVacios(correo:String, password:String): Boolean{
        return correo.isNotEmpty()&&password.isNotEmpty()
    }

    private fun login(correo:String, password:String){

        auth.signInWithEmailAndPassword(correo,password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful){
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(applicationContext,"El loguin ha fallat!", Toast.LENGTH_LONG).show()
                }
            }
    }
}