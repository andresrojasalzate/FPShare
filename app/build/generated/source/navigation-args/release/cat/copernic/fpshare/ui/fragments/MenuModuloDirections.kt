package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class MenuModuloDirections private constructor() {
  private data class ActionMenuModuloToListaUFs(
    public val cicloId: String,
    public val moduloId: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_menuModulo_to_listaUFs

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("cicloId", this.cicloId)
        result.putString("moduloId", this.moduloId)
        return result
      }
  }

  public companion object {
    public fun actionMenuModuloToListaUFs(cicloId: String, moduloId: String): NavDirections =
        ActionMenuModuloToListaUFs(cicloId, moduloId)
  }
}
