package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class FragmentAdminCiclosDirections private constructor() {
  private data class ActionListaTagsAdministracionToFragmentAdminModulos(
    public val idCiclo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_listaTagsAdministracion_to_fragmentAdminModulos

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idCiclo", this.idCiclo)
        return result
      }
  }

  public companion object {
    public fun actionListaTagsAdministracionToFragmentAdminModulos(idCiclo: String): NavDirections =
        ActionListaTagsAdministracionToFragmentAdminModulos(idCiclo)

    public fun actionListaTagsAdministracionToCrearCiclo(): NavDirections =
        ActionOnlyNavDirections(R.id.action_listaTagsAdministracion_to_crearCiclo)
  }
}
