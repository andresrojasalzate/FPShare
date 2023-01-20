package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class CreacionForoDirections private constructor() {
  private data class ActionCreacionForoToFPHilo(
    public val idforo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_creacionForo_to_FPHilo

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idforo", this.idforo)
        return result
      }
  }

  public companion object {
    public fun actionCreacionForoToFPHilo(idforo: String): NavDirections =
        ActionCreacionForoToFPHilo(idforo)
  }
}
