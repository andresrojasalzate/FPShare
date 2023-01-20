package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class FragmentAdminPostsArgs(
  public val idCiclo: String,
  public val idModulo: String,
  public val idUf: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("idCiclo", this.idCiclo)
    result.putString("idModulo", this.idModulo)
    result.putString("idUf", this.idUf)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("idCiclo", this.idCiclo)
    result.set("idModulo", this.idModulo)
    result.set("idUf", this.idUf)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): FragmentAdminPostsArgs {
      bundle.setClassLoader(FragmentAdminPostsArgs::class.java.classLoader)
      val __idCiclo : String?
      if (bundle.containsKey("idCiclo")) {
        __idCiclo = bundle.getString("idCiclo")
        if (__idCiclo == null) {
          throw IllegalArgumentException("Argument \"idCiclo\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idCiclo\" is missing and does not have an android:defaultValue")
      }
      val __idModulo : String?
      if (bundle.containsKey("idModulo")) {
        __idModulo = bundle.getString("idModulo")
        if (__idModulo == null) {
          throw IllegalArgumentException("Argument \"idModulo\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idModulo\" is missing and does not have an android:defaultValue")
      }
      val __idUf : String?
      if (bundle.containsKey("idUf")) {
        __idUf = bundle.getString("idUf")
        if (__idUf == null) {
          throw IllegalArgumentException("Argument \"idUf\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idUf\" is missing and does not have an android:defaultValue")
      }
      return FragmentAdminPostsArgs(__idCiclo, __idModulo, __idUf)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): FragmentAdminPostsArgs {
      val __idCiclo : String?
      if (savedStateHandle.contains("idCiclo")) {
        __idCiclo = savedStateHandle["idCiclo"]
        if (__idCiclo == null) {
          throw IllegalArgumentException("Argument \"idCiclo\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idCiclo\" is missing and does not have an android:defaultValue")
      }
      val __idModulo : String?
      if (savedStateHandle.contains("idModulo")) {
        __idModulo = savedStateHandle["idModulo"]
        if (__idModulo == null) {
          throw IllegalArgumentException("Argument \"idModulo\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idModulo\" is missing and does not have an android:defaultValue")
      }
      val __idUf : String?
      if (savedStateHandle.contains("idUf")) {
        __idUf = savedStateHandle["idUf"]
        if (__idUf == null) {
          throw IllegalArgumentException("Argument \"idUf\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idUf\" is missing and does not have an android:defaultValue")
      }
      return FragmentAdminPostsArgs(__idCiclo, __idModulo, __idUf)
    }
  }
}
