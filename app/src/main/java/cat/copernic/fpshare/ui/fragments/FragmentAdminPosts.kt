package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.adapters.PubliAdminAdapter
import cat.copernic.fpshare.databinding.FragmentAdminPostsBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FragmentAdminPosts : Fragment(), PubliAdminAdapter.OnItemClickListener {

    // Binding
    private var _binding: FragmentAdminPostsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    // Firebase
    val bd = FirebaseFirestore.getInstance()

    private lateinit var adapter: PubliAdminAdapter
    private lateinit var postsList: Deferred<MutableList<Publicacion>>

    // Botones
    private lateinit var botonEditUF: Button
    private lateinit var botonDeleteUF: Button

    // ? var publi = ""

    // Args
    private val args: FragmentAdminPostsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializadores()
        listeners()

        // Corrutina para la lectura de publicaciones en la base de datos
        lifecycleScope.launch(Dispatchers.Main) {
            postsList = async { crearMenu() }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listeners() {
        botonEditUF.setOnClickListener {
            val action =
                FragmentAdminPostsDirections.actionFragmentAdminPostsToFragmentAdminEditUF(
                    args.idCiclo, args.idModulo, args.idUf
                )
            view?.findNavController()?.navigate(action)
        }
        botonDeleteUF.setOnClickListener {
            borrarUF()
            val action =
                FragmentAdminPostsDirections.actionFragmentAdminPostsToFragmentAdminUFs(
                    args.idModulo,
                    args.idCiclo
                )
            view?.findNavController()?.navigate(action)
        }
    }

    private fun inicializadores() {
        recyclerView = binding.ViewApuntes
        botonEditUF = binding.btnEditUF
        botonDeleteUF = binding.btnDeleteUF
    }

    /**
     * Función para mostrar las publicaciones de un ciclo, modulo y UF en especifico
     */
    private suspend fun crearMenu(): MutableList<Publicacion> {
        val postsList = mutableListOf<Publicacion>()
        val publicaciones = bd.collection("Ciclos").document(args.idCiclo).collection("Modulos")
            .document(args.idModulo).collection("UFs").document(args.idUf)
            .collection("Publicaciones").get().await()
        for (document in publicaciones) {
            val idPubli = document.id
            val checked = document["checked"].toString()
            val publiDescr = document["descripcion"].toString()
            val publiLink = document["enlace"].toString()
            val publiProfile = document["perfil"].toString()
            val publiTitle = document["titulo"].toString()
            val imgPubli = document["imgPubli"].toString()
            val publi = Publicacion(
                idPubli,
                publiProfile,
                publiTitle,
                publiDescr,
                checked,
                publiLink,
                imgPubli
            )
            postsList.add(publi)
        }
        adapter = PubliAdminAdapter(postsList, this)
        binding.ViewApuntes.adapter = adapter
        binding.ViewApuntes.layoutManager = LinearLayoutManager(requireContext())


        return postsList
    }

    /**
     * Función para borrar el modulo en el que nos encontramos
     */
    private fun borrarUF() {
        bd.collection("Ciclos").document(args.idCiclo).collection("Modulos").document(args.idModulo)
            .collection("UFs").document(args.idUf).delete().addOnSuccessListener {
                Toast.makeText(context, getString(R.string.ufBorradoCorrecto), Toast.LENGTH_LONG)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(context, getString(R.string.ufBorradoError), Toast.LENGTH_LONG)
                    .show()
            }
    }

    /**
     * Navegación para ir hacia la modificación de la publicación seleccionada por el usuario
     */
    override fun onItemClick(id: String) {
        bd.collection("Ciclos").document(args.idCiclo).collection("Modulos").document(args.idModulo)
            .collection("UFs").document(args.idUf).collection("Publicaciones").document(id)
            .get().addOnSuccessListener {
                val view = binding.root
                val action =
                    FragmentAdminPostsDirections.actionFragmentAdminPostsToFragmentAdminModPost(
                        args.idCiclo,
                        args.idModulo,
                        args.idUf,
                        id
                    )
                view.findNavController().navigate(action)
            }
    }
}