package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class Renombrar_UsuarioArgs(
  public val idUsuario: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("idUsuario", this.idUsuario)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("idUsuario", this.idUsuario)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): Renombrar_UsuarioArgs {
      bundle.setClassLoader(Renombrar_UsuarioArgs::class.java.classLoader)
      val __idUsuario : String?
      if (bundle.containsKey("idUsuario")) {
        __idUsuario = bundle.getString("idUsuario")
        if (__idUsuario == null) {
          throw IllegalArgumentException("Argument \"idUsuario\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idUsuario\" is missing and does not have an android:defaultValue")
      }
      return Renombrar_UsuarioArgs(__idUsuario)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): Renombrar_UsuarioArgs {
      val __idUsuario : String?
      if (savedStateHandle.contains("idUsuario")) {
        __idUsuario = savedStateHandle["idUsuario"]
        if (__idUsuario == null) {
          throw IllegalArgumentException("Argument \"idUsuario\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idUsuario\" is missing and does not have an android:defaultValue")
      }
      return Renombrar_UsuarioArgs(__idUsuario)
    }
  }
}
