package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class FragmentAdminModulosDirections private constructor() {
  private data class ActionFragmentAdminModulosToFragmentAdminUFs(
    public val idModulo: String,
    public val idCiclo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminModulos_to_fragmentAdminUFs

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idModulo", this.idModulo)
        result.putString("idCiclo", this.idCiclo)
        return result
      }
  }

  private data class ActionFragmentAdminModulosToCrearModulo(
    public val idCiclo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminModulos_to_crearModulo

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idCiclo", this.idCiclo)
        return result
      }
  }

  private data class ActionFragmentAdminModulosToFragmentAdminEditCicle(
    public val idCiclo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminModulos_to_fragmentAdminEditCicle

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idCiclo", this.idCiclo)
        return result
      }
  }

  public companion object {
    public fun actionFragmentAdminModulosToFragmentAdminUFs(idModulo: String, idCiclo: String):
        NavDirections = ActionFragmentAdminModulosToFragmentAdminUFs(idModulo, idCiclo)

    public fun actionFragmentAdminModulosToCrearModulo(idCiclo: String): NavDirections =
        ActionFragmentAdminModulosToCrearModulo(idCiclo)

    public fun actionFragmentAdminModulosToFragmentAdminEditCicle(idCiclo: String): NavDirections =
        ActionFragmentAdminModulosToFragmentAdminEditCicle(idCiclo)

    public fun actionFragmentAdminModulosToListaTagsAdministracion(): NavDirections =
        ActionOnlyNavDirections(R.id.action_fragmentAdminModulos_to_listaTagsAdministracion)
  }
}
