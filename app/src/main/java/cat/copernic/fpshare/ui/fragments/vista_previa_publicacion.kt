package cat.copernic.fpshare.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.FragmentPantallaPrincipalBinding
import cat.copernic.fpshare.databinding.FragmentVistaPreviaPublicacionBinding

const val ENLACE = "https://github.com/github"
class vista_previa_publicacion : Fragment() {
    private var _binding: FragmentVistaPreviaPublicacionBinding? = null
    private val binding get() = _binding!!
    private lateinit var enlace: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVistaPreviaPublicacionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enlace = binding.txtLink

        enlace.setOnClickListener{
            val queryUrl: Uri = Uri.parse(ENLACE)

            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            context?.startActivity(intent)

        }

    }
}
