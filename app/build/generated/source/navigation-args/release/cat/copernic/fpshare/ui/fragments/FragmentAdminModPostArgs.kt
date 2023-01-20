package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class FragmentAdminModPostArgs(
  public val idCiclo: String,
  public val idModulo: String,
  public val idUf: String,
  public val idPubli: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("idCiclo", this.idCiclo)
    result.putString("idModulo", this.idModulo)
    result.putString("idUf", this.idUf)
    result.putString("idPubli", this.idPubli)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("idCiclo", this.idCiclo)
    result.set("idModulo", this.idModulo)
    result.set("idUf", this.idUf)
    result.set("idPubli", this.idPubli)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): FragmentAdminModPostArgs {
      bundle.setClassLoader(FragmentAdminModPostArgs::class.java.classLoader)
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
      val __idPubli : String?
      if (bundle.containsKey("idPubli")) {
        __idPubli = bundle.getString("idPubli")
        if (__idPubli == null) {
          throw IllegalArgumentException("Argument \"idPubli\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idPubli\" is missing and does not have an android:defaultValue")
      }
      return FragmentAdminModPostArgs(__idCiclo, __idModulo, __idUf, __idPubli)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): FragmentAdminModPostArgs {
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
      val __idPubli : String?
      if (savedStateHandle.contains("idPubli")) {
        __idPubli = savedStateHandle["idPubli"]
        if (__idPubli == null) {
          throw IllegalArgumentException("Argument \"idPubli\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idPubli\" is missing and does not have an android:defaultValue")
      }
      return FragmentAdminModPostArgs(__idCiclo, __idModulo, __idUf, __idPubli)
    }
  }
}
