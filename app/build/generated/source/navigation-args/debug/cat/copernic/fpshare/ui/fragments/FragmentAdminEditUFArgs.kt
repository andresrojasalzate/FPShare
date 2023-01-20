package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class FragmentAdminEditUFArgs(
  public val idCiclo: String,
  public val idModulo: String,
  public val idUF: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("idCiclo", this.idCiclo)
    result.putString("idModulo", this.idModulo)
    result.putString("idUF", this.idUF)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("idCiclo", this.idCiclo)
    result.set("idModulo", this.idModulo)
    result.set("idUF", this.idUF)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): FragmentAdminEditUFArgs {
      bundle.setClassLoader(FragmentAdminEditUFArgs::class.java.classLoader)
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
      val __idUF : String?
      if (bundle.containsKey("idUF")) {
        __idUF = bundle.getString("idUF")
        if (__idUF == null) {
          throw IllegalArgumentException("Argument \"idUF\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idUF\" is missing and does not have an android:defaultValue")
      }
      return FragmentAdminEditUFArgs(__idCiclo, __idModulo, __idUF)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): FragmentAdminEditUFArgs {
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
      val __idUF : String?
      if (savedStateHandle.contains("idUF")) {
        __idUF = savedStateHandle["idUF"]
        if (__idUF == null) {
          throw IllegalArgumentException("Argument \"idUF\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idUF\" is missing and does not have an android:defaultValue")
      }
      return FragmentAdminEditUFArgs(__idCiclo, __idModulo, __idUF)
    }
  }
}
