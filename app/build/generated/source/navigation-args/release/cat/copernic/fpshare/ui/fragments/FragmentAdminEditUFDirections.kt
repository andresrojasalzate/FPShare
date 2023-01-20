package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class FragmentAdminEditUFDirections private constructor() {
  private data class ActionFragmentAdminEditUFToFragmentAdminPosts(
    public val idCiclo: String,
    public val idModulo: String,
    public val idUf: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminEditUF_to_fragmentAdminPosts

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idCiclo", this.idCiclo)
        result.putString("idModulo", this.idModulo)
        result.putString("idUf", this.idUf)
        return result
      }
  }

  public companion object {
    public fun actionFragmentAdminEditUFToFragmentAdminPosts(
      idCiclo: String,
      idModulo: String,
      idUf: String
    ): NavDirections = ActionFragmentAdminEditUFToFragmentAdminPosts(idCiclo, idModulo, idUf)
  }
}
