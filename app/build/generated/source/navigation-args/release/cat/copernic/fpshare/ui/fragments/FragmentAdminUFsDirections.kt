package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class FragmentAdminUFsDirections private constructor() {
  private data class ActionFragmentAdminUFsToCrearUF(
    public val idCiclo: String,
    public val idModulo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminUFs_to_crearUF

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idCiclo", this.idCiclo)
        result.putString("idModulo", this.idModulo)
        return result
      }
  }

  private data class ActionFragmentAdminUFsToFragmentAdminPosts(
    public val idCiclo: String,
    public val idModulo: String,
    public val idUf: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminUFs_to_fragmentAdminPosts

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idCiclo", this.idCiclo)
        result.putString("idModulo", this.idModulo)
        result.putString("idUf", this.idUf)
        return result
      }
  }

  private data class ActionFragmentAdminUFsToFragmentAdminEditModule(
    public val idModulo: String,
    public val idCiclo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminUFs_to_fragmentAdminEditModule

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idModulo", this.idModulo)
        result.putString("idCiclo", this.idCiclo)
        return result
      }
  }

  private data class ActionFragmentAdminUFsToFragmentAdminModulos(
    public val idCiclo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminUFs_to_fragmentAdminModulos

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idCiclo", this.idCiclo)
        return result
      }
  }

  public companion object {
    public fun actionFragmentAdminUFsToCrearUF(idCiclo: String, idModulo: String): NavDirections =
        ActionFragmentAdminUFsToCrearUF(idCiclo, idModulo)

    public fun actionFragmentAdminUFsToFragmentAdminPosts(
      idCiclo: String,
      idModulo: String,
      idUf: String
    ): NavDirections = ActionFragmentAdminUFsToFragmentAdminPosts(idCiclo, idModulo, idUf)

    public fun actionFragmentAdminUFsToFragmentAdminEditModule(idModulo: String, idCiclo: String):
        NavDirections = ActionFragmentAdminUFsToFragmentAdminEditModule(idModulo, idCiclo)

    public fun actionFragmentAdminUFsToFragmentAdminModulos(idCiclo: String): NavDirections =
        ActionFragmentAdminUFsToFragmentAdminModulos(idCiclo)
  }
}
