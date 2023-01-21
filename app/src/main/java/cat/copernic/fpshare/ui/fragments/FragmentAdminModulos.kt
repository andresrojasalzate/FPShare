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
import cat.copernic.fpshare.R
import cat.copernic.fpshare.adapters.ModulAdminAdapter
import cat.copernic.fpshare.databinding.FragmentAdminModulosBinding
import cat.copernic.fpshare.modelo.Modul
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Fragment de pantalla de Administración de Modulos
 *
 * @author FPShare
 */
class FragmentAdminModulos : Fragment(), ModulAdminAdapter.OnItemClickListener {

    // Binding
    private var _binding: FragmentAdminModulosBinding? = null
    private val binding get() = _binding!!

    // Firebase
    private val bd = FirebaseFirestore.getInstance()

    private lateinit var moduloList: Deferred<MutableList<Modul>>
    private lateinit var adapterM: ModulAdminAdapter

    // Botones
    private lateinit var botonAddModulo: Button
    private lateinit var botonEditCiclo: Button
    private lateinit var botonBorrarCiclo: Button

    private lateinit var recyclerViewModulos: RecyclerView

    // Args
    private val args: FragmentAdminModulosArgs by navArgs()

    /**
     * Con esta función mostraremos el diseño de la pantalla ,mediante un View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminModulosBinding.inflate(inflater, container, false)
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
         * Corrutina para cargar la lectura de Modulos
         */
        lifecycleScope.launch(Dispatchers.Main) {
            moduloList = async { crearModulos() }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inicializadoresButton() {
        // inicializar botones
        botonAddModulo = binding.buttonAddModule
        botonEditCiclo = binding.btnEditCicle
        botonBorrarCiclo = binding.btnDelete
    }

    private fun inicializadoresRW() {
        recyclerViewModulos = binding.recyclerViewModulos
    }

    private fun listeners() {
        /**
         * Botón para enviar al usuario a la pantalla CrearModulo para que pueda crear un
         * nuevo modulo en la base de datos
         */
        botonAddModulo.setOnClickListener {
            val action =
                FragmentAdminModulosDirections.actionFragmentAdminModulosToCrearModulo(args.idCiclo)
            view?.findNavController()?.navigate(action)
        }
        /**
         * Al estar dentro del ciclo, este botón permite la edición del ciclo, nos manda hacia
         * la pantalla de Editar Ciclo para que el usuario pueda modificar
         */
        botonEditCiclo.setOnClickListener {
            val action =
                FragmentAdminModulosDirections.actionFragmentAdminModulosToFragmentAdminEditCicle(
                    args.idCiclo
                )
            view?.findNavController()?.navigate(action)
        }
        /**
         * Al estar dentro del ciclo, este botón permite el borrado del ciclo.
         */
        botonBorrarCiclo.setOnClickListener {
            borrarCiclo()
            val action =
                FragmentAdminModulosDirections.actionFragmentAdminModulosToListaTagsAdministracion()
            view?.findNavController()?.navigate(action)
        }

    }

    /**
     * Función para la lectura de la subcolección de Modulos, utilizando la ID que nos ha dado
     * el onClickListener de Ciclos
     *
     * @return moduloList
     */
    private suspend fun crearModulos(): MutableList<Modul> {
        val moduloList = mutableListOf<Modul>()
        val idCiclo = args.idCiclo

        val modulos = bd.collection("Ciclos").document(idCiclo).collection("Modulos").get().await()
        for (document in modulos) {
            val idModul = document.id
            val nombreModul = document["nombre"].toString()
            val modulo = Modul(
                idModul, nombreModul
            )
            moduloList.add(modulo)
        }
        adapterM = ModulAdminAdapter(moduloList, this)
        binding.recyclerViewModulos.adapter = adapterM
        binding.recyclerViewModulos.layoutManager = LinearLayoutManager(requireContext())

        return moduloList
    }

    /**
     * Función para borrar el ciclo en el que nos encontramos
     */
    private fun borrarCiclo() {
        bd.collection("Ciclos").document(args.idCiclo).delete()
            .addOnSuccessListener {
                view?.let { it1 ->
                    Snackbar.make(
                        it1,
                        getString(R.string.cicloBorradoCorrecto),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
            .addOnFailureListener {
                view?.let { it1 ->
                    Snackbar.make(
                        it1,
                        getString(R.string.cicloBorrarError),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }

    /**
     * Navegación hacia UFs
     *
     * @param id
     */
    override fun onItemClick(id: String) {
        val view = binding.root
        val action = FragmentAdminModulosDirections.actionFragmentAdminModulosToFragmentAdminUFs(
            id, args.idCiclo
        )
        view.findNavController().navigate(action)
    }
}