package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class MenuUFDirections private constructor() {
  private data class ActionListaUFsToMenuApuntes(
    public val cicloId: String,
    public val moduloId: String,
    public val ufId: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_listaUFs_to_menuApuntes

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("cicloId", this.cicloId)
        result.putString("moduloId", this.moduloId)
        result.putString("ufId", this.ufId)
        return result
      }
  }

  public companion object {
    public fun actionListaUFsToMenuApuntes(
      cicloId: String,
      moduloId: String,
      ufId: String
    ): NavDirections = ActionListaUFsToMenuApuntes(cicloId, moduloId, ufId)
  }
}
