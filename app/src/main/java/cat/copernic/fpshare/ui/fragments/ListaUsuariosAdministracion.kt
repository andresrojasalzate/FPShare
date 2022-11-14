package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.UserAdapter
import cat.copernic.fpshare.modelo.User
import cat.copernic.fpshare.databinding.FragmentListaUsuariosAdministracionBinding

class ListaUsuariosAdministracion : Fragment() {
    private var _binding: FragmentListaUsuariosAdministracionBinding? = null
    private val binding get() = _binding!!
    private lateinit var botonRename : Button
    private lateinit var botonDelete : Button
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaUsuariosAdministracionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        botonRename = binding.buttonRenameUser
        botonDelete = binding.buttonDeleteUser
        recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = UserAdapter(obtenerUsuarios())

        botonRename.setOnClickListener {
            val action =
                ListaUsuariosAdministracionDirections.actionFragmentListaUsuariosAdministracionToRenameUser()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun obtenerUsuarios(): MutableList<User>{

        val usuarios = mutableListOf<User>()

        /*val usuario1 = User(1, "Antoni", "https://blog.aulaformativa.com/wp-content/uploads/2016/08/consideraciones-mejorar-primera-experiencia-de-usuario-aplicaciones-web-perfil-usuario.jpg")
        val usuario2 = User(2, "Guille", "https://blog.aulaformativa.com/wp-content/uploads/2016/08/consideraciones-mejorar-primera-experiencia-de-usuario-aplicaciones-web-perfil-usuario.jpg")
        val usuario3 = User(3, "Fran", "https://blog.aulaformativa.com/wp-content/uploads/2016/08/consideraciones-mejorar-primera-experiencia-de-usuario-aplicaciones-web-perfil-usuario.jpg")
        val usuario4 = User(4, "Andrés", "https://blog.aulaformativa.com/wp-content/uploads/2016/08/consideraciones-mejorar-primera-experiencia-de-usuario-aplicaciones-web-perfil-usuario.jpg")
        val usuario5 = User(4,"","")
        usuarios.add(usuario1)
        usuarios.add(usuario2)
        usuarios.add(usuario3)
        usuarios.add(usuario4)

        for(num in 1..30){

            usuarios.add(User(num, "Andrés", "https://blog.aulaformativa.com/wp-content/uploads/2016/08/consideraciones-mejorar-primera-experiencia-de-usuario-aplicaciones-web-perfil-usuario.jpg"))

        }*/

        return usuarios

    }

}