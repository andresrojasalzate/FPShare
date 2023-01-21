package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class ListaForosDirections private constructor() {
  private data class ActionListaForosToFPHilo(
    public val idforo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_listaForos_to_FPHilo

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idforo", this.idforo)
        return result
      }
  }

  public companion object {
    public fun actionListaForosToFPHilo(idforo: String): NavDirections =
        ActionListaForosToFPHilo(idforo)

    public fun actionListaForosToCreacionForo(): NavDirections =
        ActionOnlyNavDirections(R.id.action_listaForos_to_creacionForo)
  }
}
