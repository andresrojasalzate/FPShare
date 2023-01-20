package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class FragmentAdminEditModuleDirections private constructor() {
  private data class ActionFragmentAdminEditModuleToFragmentAdminUFs(
    public val idModulo: String,
    public val idCiclo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminEditModule_to_fragmentAdminUFs

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idModulo", this.idModulo)
        result.putString("idCiclo", this.idCiclo)
        return result
      }
  }

  public companion object {
    public fun actionFragmentAdminEditModuleToFragmentAdminUFs(idModulo: String, idCiclo: String):
        NavDirections = ActionFragmentAdminEditModuleToFragmentAdminUFs(idModulo, idCiclo)
  }
}
