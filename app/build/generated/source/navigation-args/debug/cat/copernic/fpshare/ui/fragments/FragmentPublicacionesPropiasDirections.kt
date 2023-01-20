package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class FragmentPublicacionesPropiasDirections private constructor() {
  private data class ActionFragmentPublicacionesPropiasToFragmentAdminModPost(
    public val idCiclo: String,
    public val idModulo: String,
    public val idUf: String,
    public val idPubli: String
  ) : NavDirections {
    public override val actionId: Int =
        R.id.action_fragmentPublicacionesPropias_to_fragmentAdminModPost

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idCiclo", this.idCiclo)
        result.putString("idModulo", this.idModulo)
        result.putString("idUf", this.idUf)
        result.putString("idPubli", this.idPubli)
        return result
      }
  }

  public companion object {
    public fun actionFragmentPublicacionesPropiasToFragmentAdminModPost(
      idCiclo: String,
      idModulo: String,
      idUf: String,
      idPubli: String
    ): NavDirections = ActionFragmentPublicacionesPropiasToFragmentAdminModPost(idCiclo, idModulo,
        idUf, idPubli)
  }
}
