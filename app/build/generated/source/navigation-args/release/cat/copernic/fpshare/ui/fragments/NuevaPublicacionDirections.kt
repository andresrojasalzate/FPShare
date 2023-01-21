package cat.copernic.fpshare.ui.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R

public class NuevaPublicacionDirections private constructor() {
  public companion object {
    public fun actionNuevaPublicacionToPantallaPrincipal(): NavDirections =
        ActionOnlyNavDirections(R.id.action_nuevaPublicacion_to_pantalla_principal)
  }
}
