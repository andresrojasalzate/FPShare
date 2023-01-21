package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class MenuCiclosDirections private constructor() {
  private data class ActionMenuCiclosToMenuModulo(
    public val cicloid: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_menuCiclos_to_menuModulo

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("cicloid", this.cicloid)
        return result
      }
  }

  public companion object {
    public fun actionMenuCiclosToMenuModulo(cicloid: String): NavDirections =
        ActionMenuCiclosToMenuModulo(cicloid)
  }
}
