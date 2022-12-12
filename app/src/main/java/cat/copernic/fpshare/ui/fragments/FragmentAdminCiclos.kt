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
import cat.copernic.fpshare.adapters.UfAdminAdapter
import cat.copernic.fpshare.databinding.FragmentAdminCiclosBinding
import cat.copernic.fpshare.modelo.Cicle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FragmentAdminCiclos : Fragment(), CicleAdminAdapter.OnItemClickListener,
    UfAdminAdapter.OnItemClickListener {

    private var _binding: FragmentAdminCiclosBinding? = null
    private val binding get() = _binding!!
    private val bd = FirebaseFirestore.getInstance()

    // Listas
    private lateinit var cicloList: Deferred<MutableList<Cicle>>

    // Adaptadores
    private lateinit var adapterC: CicleAdminAdapter

    // Botones
    private lateinit var botonAddCiclo: Button
    private lateinit var botonDeleteCiclo: Button

    // RecyclerViews
    private lateinit var recyclerViewCiclos: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminCiclosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializadoresButton()
        inicializadoresRW()
        listeners()
        lifecycleScope.launch(Dispatchers.Main) {
            cicloList = async { crearCiclos() }
        }
    }

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
        botonAddCiclo.setOnClickListener {
            val action =
                FragmentAdminCiclosDirections.actionListaTagsAdministracionToCrearCiclo()
            view?.findNavController()?.navigate(action)
        }
    }

    override fun onItemClick(id: String) {
        val view = binding.root
        val action =
            FragmentAdminCiclosDirections.actionListaTagsAdministracionToFragmentAdminModulos(id)
        view.findNavController().navigate(action)
    }
}