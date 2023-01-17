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
import cat.copernic.fpshare.databinding.FragmentAdminCiclosBinding
import cat.copernic.fpshare.modelo.Cicle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Fragment de la pantalla de Adminstración de ciclos
 *
 * @author FPShare
 */
class FragmentAdminCiclos : Fragment(), CicleAdminAdapter.OnItemClickListener {

    // Binding
    private var _binding: FragmentAdminCiclosBinding? = null
    private val binding get() = _binding!!

    // Firebase
    private val bd = FirebaseFirestore.getInstance()

    // Listas
    private lateinit var cicloList: Deferred<MutableList<Cicle>>

    // Adaptadores
    private lateinit var adapterC: CicleAdminAdapter

    // Botones
    private lateinit var botonAddCiclo: Button

    // RecyclerViews
    private lateinit var recyclerViewCiclos: RecyclerView

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
        _binding = FragmentAdminCiclosBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * En esta función iniciamos  los diferentes elementos de la pantalla y creamos los listener de los eventos de los
     * elementos  de la vista
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializadoresButton()
        inicializadoresRW()
        listeners()
        /**
         * Corrutina para la lectura de Ciclos
         */
        lifecycleScope.launch(Dispatchers.Main) {
            cicloList = async { crearCiclos() }
        }
    }

    /**
     * Con esta función destruimos la vista del fragemnt y limpiamos recursos para que el sistema funcione correctamente
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inicializadoresButton() {
        // inicializar botones de ciclo
        botonAddCiclo = binding.buttonAddCiclo
    }

    private fun inicializadoresRW() {
        // inicializar recyclerViews
        recyclerViewCiclos = binding.recyclerViewCiclos
    }

    /**
     * Lectura de la colección de Ciclos de la base de datos
     *
     * @return cicloList
     */
    private suspend fun crearCiclos(): MutableList<Cicle> {
        val cicloList = mutableListOf<Cicle>()
        val ciclos = bd.collection("Ciclos").get().await()
        for (document in ciclos) {
            val idCiclo = document.id
            val nombreCiclo = document["nombre"].toString()

            cicloList.add(Cicle(idCiclo, nombreCiclo))
        }
        adapterC = CicleAdminAdapter(cicloList, this)
        binding.recyclerViewCiclos.adapter = adapterC
        binding.recyclerViewCiclos.layoutManager = LinearLayoutManager(requireContext())

        return cicloList
    }

    private fun listeners() {
        /**
         * Botón para enviar al usuario a la pantalla CrearCiclo para que pueda crear un
         * nuevo ciclo en la base de datos
         */
        botonAddCiclo.setOnClickListener {
            val action =
                FragmentAdminCiclosDirections.actionListaTagsAdministracionToCrearCiclo()
            view?.findNavController()?.navigate(action)
        }
    }

    /**
     * Navegación hacia adminModulos con el que es la ID del modulo seleccionado
     *
     * @param id
     */
    override fun onItemClick(id: String) {
        val view = binding.root
        val action =
            FragmentAdminCiclosDirections.actionListaTagsAdministracionToFragmentAdminModulos(id)
        view.findNavController().navigate(action)
    }
}