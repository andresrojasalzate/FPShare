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
import cat.copernic.fpshare.adapters.CicleAdminAdapter
import cat.copernic.fpshare.adapters.ModulAdminAdapter
import cat.copernic.fpshare.adapters.UfAdminAdapter
import cat.copernic.fpshare.databinding.FragmentTagsBinding
import cat.copernic.fpshare.modelo.Cicle
import cat.copernic.fpshare.modelo.Modul
import cat.copernic.fpshare.modelo.Publicacion
import cat.copernic.fpshare.modelo.Uf
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentTags : Fragment(), CicleAdminAdapter.OnItemClickListener,
    ModulAdminAdapter.OnItemClickListener {
    private var _binding: FragmentTagsBinding? = null
    private val binding get() = _binding!!
    private val bd = FirebaseFirestore.getInstance()

    // Listas
    private lateinit var cicloList: MutableList<Cicle>
    private lateinit var moduloList: MutableList<Modul>

    // Adaptadores

    private lateinit var adapterC: CicleAdminAdapter
    private lateinit var adapterM: ModulAdminAdapter
    private lateinit var adapterU: UfAdminAdapter

    // Botones
    private lateinit var botonAddCiclo: Button
    private lateinit var botonDeleteCiclo: Button
    private lateinit var botonAddModulo: Button
    private lateinit var botonDeleteModulo: Button
    private lateinit var botonAddUF: Button
    private lateinit var botonDeleteUF: Button

    // RecyclerViews
    private lateinit var recyclerViewCiclos: RecyclerView
    private lateinit var recyclerViewModulos: RecyclerView
    private lateinit var recyclerViewUFs: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializadoresButton()
        inicializadoresRW()
        listeners()
        lifecycleScope.launch(Dispatchers.Main) {
            cicloList = withContext(Dispatchers.IO) { crearCiclos() }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            moduloList = withContext(Dispatchers.IO) { crearModulos() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inicializadoresButton() {
        // inicializar botones de ciclo
        botonAddCiclo = binding.buttonAddCiclo
        botonDeleteCiclo = binding.buttonDeleteCicle

        // inicializar botones de modulos
        botonAddModulo = binding.buttonAddModulo
        botonDeleteModulo = binding.buttonDeleteModule

        // inicializar botones de UFs
        //botonAddUF = binding.buttonAddUF
        //botonDeleteUF = binding.buttonDeleteUF*/
    }

    private fun inicializadoresRW() {
        // inicializar recyclerViews
        recyclerViewCiclos = binding.recyclerViewCiclos
        recyclerViewModulos = binding.recyclerViewModulos
        //recyclerViewUFs = binding.recyclerViewUFs

        // recyclerView de UFs
        //recyclerViewUFs.layoutManager = LinearLayoutManager(requireContext())
        //recyclerViewUFs.adapter = adapterU
    }

    private fun crearCiclos(): MutableList<Cicle> {
        val cicloList = mutableListOf<Cicle>()
        bd.collection("Ciclos")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val idCiclo = document.id
                    val nombreCiclo = document["nombre"].toString()
                    val ciclo = Cicle(
                        idCiclo, nombreCiclo, listOf(
                            Modul(
                                "", "", listOf(
                                    Uf(
                                        "", "", listOf(
                                            Publicacion("", "", "", "", "", "")
                                        )
                                    )
                                )
                            )
                        )
                    )
                    cicloList.add(ciclo)
                }
                adapterC = CicleAdminAdapter(cicloList, this)
                binding.recyclerViewCiclos.adapter = adapterC
                binding.recyclerViewCiclos.layoutManager = LinearLayoutManager(requireContext())
            }

        return cicloList
    }

    private fun crearModulos(): MutableList<Modul> {
        val moduloList = mutableListOf<Modul>()
        bd.collection("Ciclos").document().collection("Modulos")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val idModul = document.id
                    val nombreModul = document["nombre"].toString()
                    val modulo = Modul(
                        idModul,
                        nombreModul,
                        listOf(Uf("", "", listOf(Publicacion("", "", "", "", "", ""))))
                    )
                    moduloList.add(modulo)
                }
                adapterM = ModulAdminAdapter(moduloList, this)
                binding.recyclerViewModulos.adapter = adapterM
                binding.recyclerViewModulos.layoutManager = LinearLayoutManager(requireContext())
            }
        return moduloList
    }

    private fun listeners() {
        botonAddCiclo.setOnClickListener() {
            val action =
                FragmentTagsDirections.actionListaTagsAdministracionToCrearCiclo()
            view?.findNavController()?.navigate(action)
        }
        botonAddModulo.setOnClickListener() {
            val action =
                FragmentTagsDirections.actionListaTagsAdministracionToCrearModulo()
            view?.findNavController()?.navigate(action)
        }
        /*botonAddUF.setOnClickListener() {
            val action =
                FragmentTagsDirections.actionListaTagsAdministracionToCrearUF()
            view?.findNavController()?.navigate(action)
        } */
    }

    override fun onItemClick(id: String) {
        TODO("Not yet implemented")
    }
}