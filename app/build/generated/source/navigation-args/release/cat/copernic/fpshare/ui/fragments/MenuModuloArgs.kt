package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class MenuModuloArgs(
  public val cicloid: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("cicloid", this.cicloid)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("cicloid", this.cicloid)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): MenuModuloArgs {
      bundle.setClassLoader(MenuModuloArgs::class.java.classLoader)
      val __cicloid : String?
      if (bundle.containsKey("cicloid")) {
        __cicloid = bundle.getString("cicloid")
        if (__cicloid == null) {
          throw IllegalArgumentException("Argument \"cicloid\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"cicloid\" is missing and does not have an android:defaultValue")
      }
      return MenuModuloArgs(__cicloid)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): MenuModuloArgs {
      val __cicloid : String?
      if (savedStateHandle.contains("cicloid")) {
        __cicloid = savedStateHandle["cicloid"]
        if (__cicloid == null) {
          throw IllegalArgumentException("Argument \"cicloid\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"cicloid\" is missing and does not have an android:defaultValue")
      }
      return MenuModuloArgs(__cicloid)
    }
  }
}
