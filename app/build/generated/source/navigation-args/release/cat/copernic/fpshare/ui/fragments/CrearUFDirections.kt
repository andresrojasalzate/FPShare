package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class CrearUFDirections private constructor() {
  private data class ActionCrearUFToFragmentAdminUFs(
    public val idModulo: String,
    public val idCiclo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_crearUF_to_fragmentAdminUFs

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idModulo", this.idModulo)
        result.putString("idCiclo", this.idCiclo)
        return result
      }
  }

  public companion object {
    public fun actionCrearUFToFragmentAdminUFs(idModulo: String, idCiclo: String): NavDirections =
        ActionCrearUFToFragmentAdminUFs(idModulo, idCiclo)
  }
}
