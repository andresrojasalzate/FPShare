package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class ListaUsuariosAdministracionDirections private constructor() {
  private data class ActionListaUsuariosAdministracionToMostarInfoUsuario(
    public val email: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_listaUsuariosAdministracion_to_mostarInfoUsuario

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("email", this.email)
        return result
      }
  }

  public companion object {
    public fun actionListaUsuariosAdministracionToMostarInfoUsuario(email: String): NavDirections =
        ActionListaUsuariosAdministracionToMostarInfoUsuario(email)
  }
}
