package cat.copernic.fpshare.ui.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R

public class pantalla_principalDirections private constructor() {
  public companion object {
    public fun actionPantallaPrincipalToLogin(): NavDirections =
        ActionOnlyNavDirections(R.id.action_pantalla_principal_to_login)
  }
}
