package cat.copernic.fpshare.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.FragmentCreacionForoBinding
import cat.copernic.fpshare.modelo.Foro
import cat.copernic.fpshare.modelo.Mensaje
import cat.copernic.fpshare.modelo.Utils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Clase de la pantalla de Creación de Foro
 *
 * @author FPShare
 */
class CreacionForo : Fragment() {
    private var _binding: FragmentCreacionForoBinding? = null
    private val binding get() = _binding!!
    private lateinit var titulo: EditText
    private lateinit var descripcion: EditText
    private lateinit var boton: Button
    private lateinit var utils: Utils
    private var bd = FirebaseFirestore.getInstance()
    private var user = Firebase.auth.currentUser
    private val args: CreacionForoArgs by navArgs()

    /**
     * Con esta función mostraremos el diseño de la pantalla ,mediante un View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreacionForoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    /**
     * En esta función iniciamos  los diferentes elementos de la pantalla y creamos los listener de los eventos de los
     * elementos  de la vista
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //llamamos a la función
        inicializar()

        //establecemos un limite de caracteres para el editText del titulo
        titulo.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(utils.maxlengthTitulo))

        //establecemos un limite de caracters para el editText de la decripción
        descripcion.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(utils.maxlengthDescripcion))

        //Cuando pulsamos el boton de crear foro
        boton.setOnClickListener {

            //comprobamos que los campos no esten vacios

            if ((titulo.text.toString().isNotEmpty() && titulo.text.toString().isNotBlank())&&
                (descripcion.text.toString().isNotEmpty() && descripcion.text.toString().isNotBlank())) {
                lifecycleScope.launch(Dispatchers.Main) {
                    async{
                         //llamamos a la función
                         crearForo()
                     }
                }


            } else{
                crearalerta()
            }
        }
    }

    /**
     * En esta fUnción creamos una lerta que se mostrará cuando el compo de titulo o descripión esten vacios
     */
    private fun crearalerta() {
        //establecemos el titulo de la alerta y su descripción
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.campos_vacios))
        builder.setMessage(getString(R.string.aviso_campos_vacios))

        builder.setPositiveButton(getString(R.string.aceptat)) { dialog, which ->
        }
        //creamos la alerta y la mostramos
        val dialog: AlertDialog = builder.create()
        dialog.show()

        }

    /**
     * Con esta función destruimos la vista del fragemnt y limpiamos recursos para que el sistema funcione correctamente
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Con esta función inicilizamos los items de la pantalla
     */
    private fun inicializar(){

        //inicializamos los items del layout
        titulo = binding.txtThreadInput
        descripcion = binding.txtDescriptionInput
        boton = binding.btnSave
        //creamos una instancia de la clase utils
        utils = Utils()

    }

    /**
     * Con esta función creamos el foro y lo subimos a la base de datos
     */
    private suspend fun crearForo() {
        //cogemos el email del usuario registrado y lo asignamos a una variable
        val email = user?.email.toString()

        //recogemos los foros
        val forosBd = bd.collection("Foros").get().await()
            val foros = ArrayList<Foro>()
                for (document in forosBd) {
                    val wallitem = document.toObject(Foro::class.java)
                    wallitem.id = document.id.toInt()
                    wallitem.titulo = document["titulo"].toString()
                    wallitem.descripcion = document["descripcion"].toString()
                    wallitem.emailautor = document["emailautor"].toString()
                    foros.add(wallitem)

                }
                val idtxt :String
                var idint :Int
                //comprobamos que la lista de foros no esta vacia
                if (foros.isNotEmpty()) {
                    //ordenamos el array por el id del foro
                    foros.sortWith(compareBy({ it.id }))
                    //cogemos el ultimo
                    idint = foros.get(foros.size - 1).id
                    //le añadimos uno
                    idint += 1
                    idtxt = idint.toString()
                } else {
                    //si esta vacia ponemos el numero 1
                    idint = 1
                    idtxt = "1"
                }

                //recogemos el el nombre y apelllido del usuario de la base de datos
               val it =  bd.collection("Usuarios").document(user?.email.toString()).get().await()
                    val usuario = it["nombre"].toString() + " " + it["apellidos"]

                    //creamos el objeto del foro
                    val foro = Foro(idint, titulo.text.toString(), descripcion.text.toString(), email, usuario)
                    //creamos el objeto de un mensaje para añadirlo a la subcoleccion del foro
                    val mensajeInicial = Mensaje("shtht", "mensaje de prueba", "fff")

                    //creamos el foro en la base de datos
                    bd.collection("Foros").document(idtxt).set(foro)

                    //creamos la subcoleccion de mensajes en el foro que creamos antes
                    bd.collection("Foros").document(idtxt).collection("Mensajes").add(mensajeInicial)
                   //lamamos a la función
                    cambiarPantalla(idtxt)




            }

    /**
     * Con esta función cambiamos de pantalla  una vez creado el foro
     *
     * @param id
     */
    private fun cambiarPantalla(id: String){
        //cambiamos dde pantalla a la FPHilo
        val action =
            CreacionForoDirections.actionCreacionForoToFPHilo(id, args.fragment)
        view?.findNavController()?.navigate(action)
    }

    }





