package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
// import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.PubliAdapter
import cat.copernic.fpshare.adapters.PubliAdminAdapter
// import cat.copernic.fpshare.adapters.PubliAdminAdapter
import cat.copernic.fpshare.databinding.FragmentPublicacionesPropiasBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FragmentPublicacionesPropias : Fragment(), PubliAdminAdapter.OnItemClickListener {

    private var _binding: FragmentPublicacionesPropiasBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PubliAdminAdapter
    private lateinit var arrayList: ArrayList<String>
    private lateinit var publiList: Deferred<MutableList<Publicacion>>

    val bd = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPublicacionesPropiasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.publicacionesPropiasView

        /***
         * Cargamos las publicaciones del usuario tal y como hacemos en otras pantallas
         * con una corrutina
         */
        try {
            lifecycleScope.launch(Dispatchers.Main) {
                publiList = async { crearPublicaciones() }
            }
        } catch (_: Exception) {

        }
    }

    private suspend fun crearPublicaciones(): MutableList<Publicacion> {
        val publiList = mutableListOf<Publicacion>()
        arrayList = ArrayList()
        /***
         * Guardamos la ruta en una variable
         */
        val ciclo = bd.collection("Ciclos").get().await()
        /**
         * Recorremos la variable ciclos.
         */
        for (doc1 in ciclo) {
            /**
             * Una vez que estamos recorriendo la variable ciclo, podemos consultar la id
             * del documento que estamos recorriendo, pero antes filtramos los documentos
             * para que solamente nos aparezcan las que esten vinculadas al usuario que
             * actualmente tiene iniciada la sesión.
             */
            val modulo = bd.collection("Ciclos").document(doc1.id)
                .collection("Modulos").get().await()
            for (doc2 in modulo) {
                val uf = bd.collection("Ciclos").document(doc1.id).collection("Modulos")
                    .document(doc2.id)
                    .collection("UFs").get().await()
                for (doc3 in uf) {
                    val publi = bd.collection("Ciclos").document(doc1.id).collection("Modulos")
                        .document(doc2.id)
                        .collection("UFs").document(doc3.id).collection("Publicaciones")
                        .whereEqualTo("imgPubli", FirebaseAuth.getInstance().currentUser?.email)
                        .get().await()
                    for (doc4 in publi) {
                        /***
                         * Hacemos esto consecutivamente hasta llegar a las publicaciones.
                         * Una vez que llegamos aqui, recogemos todos los valores y guardamos las
                         * publicaciones filtradas en una MutableList<Publicacion>.
                         */
                        val idPubli = doc4.id
                        val checked = doc4["checked"].toString()
                        val publiDescr = doc4["descripcion"].toString()
                        val publiLink = doc4["enlace"].toString()
                        val publiProfile = doc4["perfil"].toString()
                        val publiTitle = doc4["titulo"].toString()
                        /***
                         * Añadimos el titulo de la publicacion para poder encontrarlo en el buscador.
                         */
                        arrayList.add(publiTitle)
                        val imgPubli = doc4["imgPubli"].toString()
                        val publicacion = Publicacion(
                            idPubli,
                            publiProfile,
                            publiTitle,
                            publiDescr,
                            checked,
                            publiLink,
                            imgPubli
                        )

                        /***
                         * Cargamos la lista en el adapter para mostrar todas las publicaciones.
                         */
                        adapter = PubliAdminAdapter(publiList, this)
                        try {
                            binding.publicacionesPropiasView.adapter = adapter
                            binding.publicacionesPropiasView.layoutManager =
                                LinearLayoutManager(requireContext())
                            publiList.add(publicacion)
                        } catch (e: NullPointerException) {
                            println("error")
                        }

                    }

                }
            }
        }
        return publiList
    }

    /**
     * Navegación para ir hacia la modificación de la publicación seleccionada por el usuario
     */
    override fun onItemClick(id: String) {
        val idCiclo = bd.collection("Ciclos").document().collection("Modulos").document()
            .collection("UFs").document().collection("Publicaciones").document(id).get()
            .addOnSuccessListener {
                it["idCiclo"].toString()

            }
        val idModulo = bd.collection("Ciclos").document().collection("Modulos").document()
            .collection("UFs").document().collection("Publicaciones").document(id).get()
            .addOnSuccessListener {

                it["idModulo"].toString()

            }
        val idUF = bd.collection("Ciclos").document().collection("Modulos").document()
            .collection("UFs").document().collection("Publicaciones").document(id).get()
            .addOnSuccessListener {
                it["idUf"].toString()
            }

        bd.collection("Ciclos").document().collection("Modulos").document()
            .collection("UFs").document().collection("Publicaciones").document(id)
            .get().addOnSuccessListener {
                val view = binding.root
                val action =
                    FragmentPublicacionesPropiasDirections.actionFragmentPublicacionesPropiasToFragmentAdminModPost(
                        idCiclo.toString(),
                        idModulo.toString(),
                        idUF.toString(),
                        id
                    )
                view.findNavController().navigate(action)
            }
    }
}
