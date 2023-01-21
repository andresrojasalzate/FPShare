package cat.copernic.fpshare.ui.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R

public class CrearCicloDirections private constructor() {
  public companion object {
    public fun actionCrearCicloToListaTagsAdministracion(): NavDirections =
        ActionOnlyNavDirections(R.id.action_crearCiclo_to_listaTagsAdministracion)
  }
}
