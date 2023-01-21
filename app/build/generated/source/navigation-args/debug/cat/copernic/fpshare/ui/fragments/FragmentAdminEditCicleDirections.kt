package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class FragmentAdminEditCicleDirections private constructor() {
  private data class ActionFragmentAdminEditCicleToFragmentAdminModulos(
    public val idCiclo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminEditCicle_to_fragmentAdminModulos

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idCiclo", this.idCiclo)
        return result
      }
  }

  public companion object {
    public fun actionFragmentAdminEditCicleToFragmentAdminModulos(idCiclo: String): NavDirections =
        ActionFragmentAdminEditCicleToFragmentAdminModulos(idCiclo)
  }
}
