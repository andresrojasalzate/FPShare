package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.UserAdapter
import cat.copernic.fpshare.modelo.User
import cat.copernic.fpshare.databinding.FragmentListaUsuariosAdministracionBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Clase de la pantalla ListaUsuariosAdministracion
 *
 * @author FPShare
 */
class ListaUsuariosAdministracion : Fragment(), UserAdapter.OnItemClickListener {
    private var _binding: FragmentListaUsuariosAdministracionBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    private var bd = FirebaseFirestore.getInstance()

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
        _binding = FragmentListaUsuariosAdministracionBinding.inflate(inflater, container, false)
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

            inicializar()
        lifecycleScope.launch(Dispatchers.Main) {
            async{llamarecycleview()}
        }
    }

    /**
     * Con esta función destruimos la vista del fragemnt y limpiamos recursos para que el sistema funcione correctamente
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private suspend fun llamarecycleview(){
        val userList = ArrayList<User>()
        val adapterUser = UserAdapter(userList, this)

        val usuarios = bd.collection("Usuarios").get().await()
            for (document in usuarios){
                val wallitem = document.toObject(User::class.java)
                wallitem.email = document.id
                wallitem.nombre = document["nombre"].toString()
                wallitem.apellidos = document["apellidos"].toString()
                wallitem.telefono = document["telefono"].toString()
                wallitem.instituto = document["instituto"].toString()
                wallitem.esAdmin = document["esAdmin"] as Boolean

                userList.add(wallitem)

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapterUser
        }

        }

   private fun inicializar(){
       recyclerView = binding.recyclerView
   }

    /**
     * Función de click
     *
     * @param email
     */
    override fun onItemClick(email: String) {
        val action = ListaUsuariosAdministracionDirections.actionListaUsuariosAdministracionToMostarInfoUsuario(email)
        view?.findNavController()?.navigate(action)
    }
}