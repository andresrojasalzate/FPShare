package cat.copernic.fpshare.ui.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R

public class MenuAdministracionDirections private constructor() {
  public companion object {
    public fun actionMenuAdministracionToListaUsuariosAdministracion(): NavDirections =
        ActionOnlyNavDirections(R.id.action_menuAdministracion_to_listaUsuariosAdministracion)

    public fun actionMenuAdministracionToListaTagsAdministracion(): NavDirections =
        ActionOnlyNavDirections(R.id.action_menuAdministracion_to_listaTagsAdministracion)
  }
}
