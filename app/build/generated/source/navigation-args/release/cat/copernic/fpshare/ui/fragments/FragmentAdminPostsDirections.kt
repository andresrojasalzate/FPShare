package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R
import kotlin.Int
import kotlin.String

public class FragmentAdminPostsDirections private constructor() {
  private data class ActionFragmentAdminPostsToFragmentAdminModPost(
    public val idCiclo: String,
    public val idModulo: String,
    public val idUf: String,
    public val idPubli: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminPosts_to_fragmentAdminModPost

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

  private data class ActionFragmentAdminPostsToFragmentAdminEditUF(
    public val idCiclo: String,
    public val idModulo: String,
    public val idUF: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminPosts_to_fragmentAdminEditUF

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idCiclo", this.idCiclo)
        result.putString("idModulo", this.idModulo)
        result.putString("idUF", this.idUF)
        return result
      }
  }

  private data class ActionFragmentAdminPostsToFragmentAdminUFs(
    public val idModulo: String,
    public val idCiclo: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentAdminPosts_to_fragmentAdminUFs

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("idModulo", this.idModulo)
        result.putString("idCiclo", this.idCiclo)
        return result
      }
  }

  public companion object {
    public fun actionFragmentAdminPostsToFragmentAdminModPost(
      idCiclo: String,
      idModulo: String,
      idUf: String,
      idPubli: String
    ): NavDirections = ActionFragmentAdminPostsToFragmentAdminModPost(idCiclo, idModulo, idUf,
        idPubli)

    public fun actionFragmentAdminPostsToFragmentAdminEditUF(
      idCiclo: String,
      idModulo: String,
      idUF: String
    ): NavDirections = ActionFragmentAdminPostsToFragmentAdminEditUF(idCiclo, idModulo, idUF)

    public fun actionFragmentAdminPostsToFragmentAdminUFs(idModulo: String, idCiclo: String):
        NavDirections = ActionFragmentAdminPostsToFragmentAdminUFs(idModulo, idCiclo)
  }
}
