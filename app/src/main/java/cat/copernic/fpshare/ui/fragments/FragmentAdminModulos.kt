package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.ModulAdminAdapter
import cat.copernic.fpshare.databinding.FragmentAdminModulosBinding
import cat.copernic.fpshare.modelo.Modul
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class FragmentAdminModulos : Fragment(), ModulAdminAdapter.OnItemClickListener {

    private var _binding: FragmentAdminModulosBinding? = null
    private val binding get() = _binding!!
    private val bd = FirebaseFirestore.getInstance()

    private lateinit var moduloList: Deferred<MutableList<Modul>>
    private lateinit var adapterM: ModulAdminAdapter

    private lateinit var botonAddModulo: Button
    private lateinit var botonDeleteModulo: Button

    private lateinit var recyclerViewModulos: RecyclerView
    private val args: FragmentAdminModulosArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminModulosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializadoresButton()
        inicializadoresRW()
        listeners()
        lifecycleScope.launch(Dispatchers.Main) {
            moduloList = async { crearModulos() }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inicializadoresButton() {
        // inicializar botones de ciclo
        botonAddModulo = binding.buttonAddModule
        botonDeleteModulo = binding.buttonDeleteModule
    }

    private fun inicializadoresRW() {
        recyclerViewModulos = binding.recyclerViewModulos
    }

    private fun listeners() {
        botonAddModulo.setOnClickListener {
            val action =
                FragmentAdminModulosDirections.actionFragmentAdminModulosToCrearModulo()
            view?.findNavController()?.navigate(action)
        }
    }

    private suspend fun crearModulos(): MutableList<Modul> {
        val moduloList = mutableListOf<Modul>()
        val idCiclo = args.idCiclo

        val modulos = bd.collection("Ciclos").document(idCiclo).collection("Modulos")
            .get().await()
        for (document in modulos) {
            val idModul = document.id
            val nombreModul = document["nombre"].toString()
            val modulo = Modul(
                idModul,
                nombreModul
            )
            moduloList.add(modulo)
        }
        adapterM = ModulAdminAdapter(moduloList, this)
        binding.recyclerViewModulos.adapter = adapterM
        binding.recyclerViewModulos.layoutManager = LinearLayoutManager(requireContext())

        return moduloList
    }

    override fun onItemClick(id: String) {
        val view = binding.root
        val action = FragmentAdminModulosDirections.actionFragmentAdminModulosToFragmentAdminUFs(
            id,
            args.idCiclo
        )
        view.findNavController().navigate(action)

    }
}