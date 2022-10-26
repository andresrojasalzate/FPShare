package cat.copernic.fpshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import cat.copernic.fpshare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var btnRegistrarse: Button
    private lateinit var InputNombre: EditText
    private lateinit var InputPassword: EditText
    private lateinit var InputMail: EditText
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        //Inicializacion de componentes
        InputNombre = findViewById(R.id.InputNombre)
        InputPassword = findViewById(R.id.InputPassword)
        InputMail = findViewById(R.id.InputMail)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)


        //Inicializacion Firebase
        auth = Firebase.auth

        btnRegistrarse.setOnClickListener(){
            var nombre = InputNombre.text.toString()
            var password = InputPassword.text.toString()
            var mail = InputMail.text.toString()

            if(campEsBuit(nombre,password,mail)){
                //Registrem a l'usuari mitjançant la funció registrar creada per nosaltres
                registrar(nombre, password,mail)
            }

        }


    }

    fun campEsBuit(nombre:String,password:String, mail:String):Boolean{
        return nombre.isNotEmpty()&&password.isNotEmpty()&&mail.isNotEmpty()
    }

    fun registrar(nombre: String,password: String,mail: String){

        auth.createUserWithEmailAndPassword(mail,password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful){ //El registre (task) s'ha completat amb exit...
                    //Anem al mainActivity des d'aquesta pantalla
                    startActivity(Intent(this,MainActivity::class.java))
                    finish() //Alliberem memòria un cop finalitzada aquesta tasca.
                }else{
                    Toast.makeText(applicationContext,"El registre ha fallat!", Toast.LENGTH_LONG).show()
                }
            }
    }


}