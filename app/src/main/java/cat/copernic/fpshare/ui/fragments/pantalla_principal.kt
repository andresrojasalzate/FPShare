package cat.copernic.fpshare.ui.fragments

import android.provider.Contacts

import android.app.ProgressDialog.show
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.*
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.adapters.PubliAdapter
import cat.copernic.fpshare.databinding.FragmentPantallaPrincipalBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class pantalla_principal() : Fragment(), SearchView.OnQueryTextListener{
    private var _binding: FragmentPantallaPrincipalBinding? = null
    private val binding get() = _binding!!

    /**
     * Muestreo Adapter con todos
     */
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PubliAdapter
    private lateinit var listview: ListView
    private lateinit var arrayList: ArrayList<String>
    private lateinit var cicloList: Deferred<MutableList<Publicacion>>
    private lateinit var searchView: SearchView



    val bd = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPantallaPrincipalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        searchView = binding.searchView
        searchView.setOnQueryTextListener(this)
        lifecycleScope.launch(Dispatchers.Main){
            cicloList = async{ crearMenu()}
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    suspend fun crearMenu(): MutableList<Publicacion>{
        var cicloList = mutableListOf<Publicacion>()
        arrayList = ArrayList()
        /***
         * Guardamos la ruta en una variable
         */
        val ciclo = bd.collection("Ciclos").get().await()

        /***
         * Recorremos la variable ciclos.
         */
        for(doc1 in ciclo){
            /***
             * Una vez que estamos recorriendo la variable ciclo, podemos consultar la id
             * del documento que estamos recorriendo.
             */
            val modulo = bd.collection("Ciclos").document(doc1.id).collection("Modulos").get().await()
            for(doc2 in modulo){
                val uf = bd.collection("Ciclos").document(doc1.id).collection("Modulos").document(doc2.id).collection("UFs").get().await()
                for(doc3 in uf){
                    val publi = bd.collection("Ciclos").document(doc1.id).collection("Modulos").document(doc2.id).collection("UFs").document(doc3.id).collection("Publicaciones").get().await()
                    for(doc4 in publi){
                        /***
                         * Hacemos esto consecutivamente hasta llegar a las publicaciones.
                         * Una vez que llegamos aqui, recogemos todos los valores y guardamos las
                         * publicaciones en una MutableList<Publicacion>.
                         */
                        val idPubli = doc4.id
                        val checked = doc4["checked"].toString()
                        val publiDescr = doc4["descripcion"].toString()
                        var publiLink = doc4["enlace"].toString()
                        val publiProfile = doc4["perfil"].toString()
                        val publiTitle = doc4["titulo"].toString()
                        /***
                         * Añadimos el titulo de la publicacion para poder encontrarlo en el buscador.
                         */
                        arrayList.add(publiTitle)
                        val imgPubli = doc4["imgPubli"].toString()
                            val publi = Publicacion(
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
                        adapter = PubliAdapter(cicloList)
                        binding.recyclerView.adapter = adapter
                        binding.recyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        cicloList.add(publi)

                    }

                }
            }
        }
        return cicloList
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }


}