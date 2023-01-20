package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class MostarInfoUsuarioDirections private constructor() {
  private data class ActionMostarInfoUsuarioToFragmentRenombrarUsuario(
    public val idUsuario: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_mostarInfoUsuario_to_fragment_renombrar_usuario

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idUsuario", this.idUsuario)
        return result
      }
  }

  public companion object {
    public fun actionMostarInfoUsuarioToFragmentRenombrarUsuario(idUsuario: String): NavDirections =
        ActionMostarInfoUsuarioToFragmentRenombrarUsuario(idUsuario)
  }
}
