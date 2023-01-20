package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class FPHiloArgs(
  public val idforo: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("idforo", this.idforo)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("idforo", this.idforo)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): FPHiloArgs {
      bundle.setClassLoader(FPHiloArgs::class.java.classLoader)
      val __idforo : String?
      if (bundle.containsKey("idforo")) {
        __idforo = bundle.getString("idforo")
        if (__idforo == null) {
          throw IllegalArgumentException("Argument \"idforo\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idforo\" is missing and does not have an android:defaultValue")
      }
      return FPHiloArgs(__idforo)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): FPHiloArgs {
      val __idforo : String?
      if (savedStateHandle.contains("idforo")) {
        __idforo = savedStateHandle["idforo"]
        if (__idforo == null) {
          throw IllegalArgumentException("Argument \"idforo\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"idforo\" is missing and does not have an android:defaultValue")
      }
      return FPHiloArgs(__idforo)
    }
  }
}
