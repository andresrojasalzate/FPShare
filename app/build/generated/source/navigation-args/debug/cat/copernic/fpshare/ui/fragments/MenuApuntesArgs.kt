package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class MenuApuntesArgs(
  public val cicloId: String,
  public val moduloId: String,
  public val ufId: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("cicloId", this.cicloId)
    result.putString("moduloId", this.moduloId)
    result.putString("ufId", this.ufId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("cicloId", this.cicloId)
    result.set("moduloId", this.moduloId)
    result.set("ufId", this.ufId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): MenuApuntesArgs {
      bundle.setClassLoader(MenuApuntesArgs::class.java.classLoader)
      val __cicloId : String?
      if (bundle.containsKey("cicloId")) {
        __cicloId = bundle.getString("cicloId")
        if (__cicloId == null) {
          throw IllegalArgumentException("Argument \"cicloId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"cicloId\" is missing and does not have an android:defaultValue")
      }
      val __moduloId : String?
      if (bundle.containsKey("moduloId")) {
        __moduloId = bundle.getString("moduloId")
        if (__moduloId == null) {
          throw IllegalArgumentException("Argument \"moduloId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"moduloId\" is missing and does not have an android:defaultValue")
      }
      val __ufId : String?
      if (bundle.containsKey("ufId")) {
        __ufId = bundle.getString("ufId")
        if (__ufId == null) {
          throw IllegalArgumentException("Argument \"ufId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"ufId\" is missing and does not have an android:defaultValue")
      }
      return MenuApuntesArgs(__cicloId, __moduloId, __ufId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): MenuApuntesArgs {
      val __cicloId : String?
      if (savedStateHandle.contains("cicloId")) {
        __cicloId = savedStateHandle["cicloId"]
        if (__cicloId == null) {
          throw IllegalArgumentException("Argument \"cicloId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"cicloId\" is missing and does not have an android:defaultValue")
      }
      val __moduloId : String?
      if (savedStateHandle.contains("moduloId")) {
        __moduloId = savedStateHandle["moduloId"]
        if (__moduloId == null) {
          throw IllegalArgumentException("Argument \"moduloId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"moduloId\" is missing and does not have an android:defaultValue")
      }
      val __ufId : String?
      if (savedStateHandle.contains("ufId")) {
        __ufId = savedStateHandle["ufId"]
        if (__ufId == null) {
          throw IllegalArgumentException("Argument \"ufId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"ufId\" is missing and does not have an android:defaultValue")
      }
      return MenuApuntesArgs(__cicloId, __moduloId, __ufId)
    }
  }
}
