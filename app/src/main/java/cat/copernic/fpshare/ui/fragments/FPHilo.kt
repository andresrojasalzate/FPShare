package cat.copernic.fpshare.ui.fragments

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.adapters.ForoAdapter
import cat.copernic.fpshare.adapters.MsgAdapter
import cat.copernic.fpshare.databinding.FragmentFpHiloBinding
import cat.copernic.fpshare.modelo.*
import cat.copernic.fpshare.ui.activities.Login
import cat.copernic.fpshare.ui.activities.MainActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
* Esta clase es de la pantalla de hilo del foro
 *
* @author FPShare
*/
class FPHilo : Fragment() {
    private var _binding: FragmentFpHiloBinding? = null
    private val binding get() = _binding!!
    private lateinit var botonSend: ImageButton
    private lateinit var inputMsg: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var textviewDescripcion : TextView
    private lateinit var textViewTitulo: TextView
    private lateinit var  textViewAutor: TextView
    private lateinit var borrarForo: ImageView
    private lateinit var editarTitulo: ImageView
    private lateinit var editarDescripcion: ImageView
    private lateinit var ediitext: EditText
    private lateinit var imputlayout: TextInputLayout
    private lateinit var enviarMensaje: ConstraintLayout
    private lateinit var mensajesList: ArrayList<Mensaje>
    private lateinit var adapter: MsgAdapter
    private lateinit var  utils: Utils
    private var bd = FirebaseFirestore.getInstance()
    private var user = Firebase.auth.currentUser

    private val args: FPHiloArgs by navArgs()

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
        _binding = FragmentFpHiloBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    /**
     * En esta función iniciamos  los diferentes elementos de la pantalla y creamos los listener de los eventos de los
     * elementos  de la vista
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //lamamos a la función inicializadores
        inicializadores()

        //lamamos a la función infoforo
        lifecycleScope.launch(Dispatchers.Main) {
           async {
               infoforo()
           }
        }

        //lamamos a la función llamarecycleviewmensajes
        lifecycleScope.launch(Dispatchers.Main) {
            async {
                llamarecycleviewmensajes()
            }
        }

        //aqui hacemos que el contendedor que tiene el edittext y el boton para enviar un nuevo mensaje este por enciama
        // de los demas items del layout para no e mezclen dañando la correcta visualización de la pantalla
        enviarMensaje.bringToFront()


        editarTitulo.setOnClickListener {

            //cuando se pusa en el icono se llama a la función
            crearAlertaConEditText(getString(R.string.nuevo_titulo_foro), 1)
        }
        editarDescripcion.setOnClickListener {

            //cuando se pusa en el icono se llama a la función
            crearAlertaConEditText(getString(R.string.nueva_descripcion), 2)

        }

        botonSend.setOnClickListener() {

            //cuando se pulsa el boton...
            //primero cogemos el texto que contiene el editText
            val texto = inputMsg.text.toString()

            //hacemos una comprobación mediante la funcion
            if (campoVacio(texto)) {

                //si la funcion nos deuelve true llamas a esta funcion
                lifecycleScope.launch(Dispatchers.Main) {
                  val x = async {
                       addMensaje(texto)
                   }
                }
                //y despues vaciamos el contenido del editText
                inputMsg.setText("")
            }
        }
        //si pulsamos el icono llamamos a la función
        borrarForo.setOnClickListener {
            crearalerta()
        }

    }

    /**
     * Esta función crea una alerta con un EditText en el
     * @param titulo el titulo de la alerta
     * @param num un numero que utlizamos para establecer el limite de caracteres que se podrá poner en el EditText
     */
    private fun crearAlertaConEditText(titulo: String, num: Int) {
        /**asignamos el valor de idForos que ha llegado a la pantalla a la variable*/
        val idForo = args.idforo

        //Se crea un objeto AlertDialog.Builder y se establece el título con el valor del parámetro
        // titulo pasado como argumento.
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(titulo)

        //Se crea un objeto EditText y se establece su tipo de entrada como "TYPE_CLASS_TEXT"
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_TEXT

        //dependiendo del numero recibido como argumento se estableera un limite de caracteres u otro para el editText
        if(num == 1){
            input.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(utils.maxlengthTitulo))
        }else{
            input.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(utils.maxlengthDescripcion))
        }

        //El objeto EditText es establecido como la vista del cuadro de diálogo de alerta.
        builder.setView(input)

        //Si el usuario pulssa Aceptar
        builder.setPositiveButton(getString(R.string.boton_positivo_alerta_borrar_forro)) { _, _ ->
            // Aqui se procesa el texto ingresado
            val inputt = input.text.toString()
            //hacemenos una comprobación con la función
            if(campoVacio(inputt)) {
                //Si el numero pasado como arguento es 1...
                if (num == 1) {
                    //editamos el titulo del foro en la base de datos
                    bd.collection("Foros").document(idForo).update("titulo", inputt)
                        .addOnSuccessListener {
                            //si se efectua el cambio cambiamos el titulo en la vista
                            textViewTitulo.setText(inputt)
                        }
                    //Si el numero pasado como arguento es 2...
                } else if (num == 2) {
                    //editamos la descripción del foro en la base de datos
                    bd.collection("Foros").document(idForo).update("descripcion", inputt)
                        .addOnSuccessListener {
                            //si se efectua el cambio cambiamos la descripción en la vista
                            textviewDescripcion.setText(inputt)
                        }
                }
            }
        }
        //Establecemos el boton cancelar
        builder.setNegativeButton(getString(R.string.cancelar)) { dialog, _ ->
            //Si lo pulsa cerramos la alerta
            dialog.cancel()
        }
        //Creamosy enseñamos la alerta
        val dialog = builder.create()
        dialog.show()

    }

    /**
     * Esta función crea otra alerta pero sin un editText, se utliza para borrar el foro
     */

    private fun crearalerta() {
        val builder = AlertDialog.Builder(requireContext())

        // Establecer el título y el mensaje de la alerta
        builder.setTitle(getString(R.string.titulo_alerta_borrar_foro))
        builder.setMessage(getString(R.string.contenido_alert_borrar_foro))

        // Establecer el botón positivo y su acción
        builder.setPositiveButton(getString(R.string.boton_positivo_alerta_borrar_forro)) { dialog, which ->
            // Acción para el botón positivo
            //borramos los mensajes deel foro que queremos borrar
           bd.collection("Foros").document(args.idforo).collection("Mensajes").get().addOnSuccessListener { documents ->
               for (document in documents){
                   document.reference.delete()
               }
           }
            //Y una vez borrados los mensajes borramos el foro
            bd.collection("Foros").document(args.idforo).delete().addOnSuccessListener {
                val action = FPHiloDirections.actionFPHiloToTusForos()
            }

        }

        // Establecer el botón negativo  solo establecemos el boton de cancelar para que aparezca en la alerta
        // y el usuario pueda cerrarla no tiene ninguna accion
        builder.setNegativeButton(getString(R.string.boton_negativo_alerta_borrar_foro)) { dialog, which ->
        }

        // Mostrar la alerta
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()

    }

    /**
     * Con est función destruimos la vista del fragemnt y limpiamos recursos para que el sistema funcione correctamente
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Esta función la utlizamos para establecer la información del foro, ademas de hacer invisibles a los iconos de borrar,
     * editar el titulo y la descripcin del foro si el usuario que accede al foro no es el autor del mismo
     */

    private suspend fun infoforo(){
        //asignamos el valor de idForos que ha llegado a la pantalla a la variable
        val idForo = args.idforo

        //asignamos el email del usuario que esta registrado
        val email = user?.email.toString()

        // hacemos una consulta a la base de datos para recuperar la información del foro que hemos seleccionado
        val document = bd.collection("Foros").document(idForo).get().await()
        //establecemos los datos en la vista
        textViewTitulo.setText(document["titulo"].toString())
        textViewAutor.setText(document["nombreApellido"].toString())
        textviewDescripcion.setText(document["descripcion"].toString())

            //compobamos que el email del creador del foro sea diferente al email del usuario actual
            if (document["emailautor"].toString() != email){
                //si es diferente...

                //haremos que no sea visibles los itm que permiten editar los datos del foro
                borrarForo.visibility = View.INVISIBLE
                borrarForo.alpha = 0f

                editarTitulo.visibility = View.INVISIBLE
                editarTitulo.alpha = 0f

                editarDescripcion.visibility = View.INVISIBLE
                editarDescripcion.alpha = 0f
            }

    }

    /**
     * Con esta función recogemos los mensajes del foro de la base de datos y los mostramos mediante un recycleview
     */
    private suspend fun llamarecycleviewmensajes(){
        //asignamos el valor de idForos que ha llegado a la pantalla a la variable
        val idForo = args.idforo

        //hacemos una consulta para recuperar todos los mensajes del foro
       val mensajes = bd.collection("Foros").document(idForo).collection("Mensajes").get().await()
            for (document in mensajes){
                //hacemos una comprtobación para excluimos el mensaje que crea al crear un foro
                if(document["emailautor"].toString() != "shtht") {
                    val wallitem = document.toObject(Mensaje::class.java)
                    mensajesList.add(wallitem)
                }
            }
            //asigamos los mensajes recuperados al recycleview para mostrarlos
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = MsgAdapter(mensajesList)

    }

    /**
     * En esta función iniciamos todos los items que se muestran en la pantalla
     */
    private fun inicializadores() {
        /**
         * inicializamos todos los items necesarios
         */
        botonSend = binding.buttonSend
        inputMsg = binding.timInput
        recyclerView = binding.recyclerViewHilo
        textViewAutor = binding.autorForo
        textViewTitulo = binding.tituloForo
        textviewDescripcion = binding.descripcion
        borrarForo = binding.borrarForo
        ediitext = binding.timInput
        imputlayout = binding.timTextHere
        editarDescripcion = binding.editarDescripcion!!
        editarTitulo = binding.editarTitulo!!
        enviarMensaje = binding.enviarMensaje!!
        mensajesList = ArrayList()
        adapter = MsgAdapter(mensajesList)
        utils = Utils()

    }

    /**
     * Con esta funcion  creamos un mensaje, lo añadimos a la base de datos y luego lo mostramos en pantalla añadiendolo
     * al recycleview
     * @param mensaje un string que es el mensaje que el usuario ha introducido mediante un EditText
     */
    private suspend fun addMensaje(mensaje: String) {
        //asignamos el valor de idForos que ha llegado a la pantalla a la variable
        val idForo = args.idforo

        //asignamos el email del usuario que esta registrado
        val email = user?.email.toString()

        //recuperamos la información del usuario
       val it = bd.collection("Usuarios").document(user?.email.toString()).get().await()
            val usuario = it["nombre"].toString() + " " + it["apellidos"]

            //creamos el objeto que contendrá el nuevo mensaje
            val nuevomensaje = Mensaje(email, mensaje, usuario)

            //lo añadimos a la base de datos
            bd.collection("Foros").document(idForo)
                .collection("Mensajes").add(nuevomensaje).addOnSuccessListener {

                    // si se añade corectamente a la base de datos se actualiza el recycleview
                    mensajesList.add(0, nuevomensaje)
                    adapter = MsgAdapter(mensajesList)
                    recyclerView.adapter = adapter
                    adapter.notifyItemInserted(0)
                }

    }

    /**
     * Con esta función combrobamos si el string pasado como argumento no esta vacio
     * @param mensaje un String que representa el mensaje que el usuario quiere enviar
     * @return devuelve un boolean que indica si el string recibido esta vacio o no
     */
    fun campoVacio(mensaje: String): Boolean {
        //comprobamos quer el mensaje no este vacion y que no solo contega espacios en blanco
        return mensaje.isNotEmpty() && mensaje.isNotBlank()
    }

}

