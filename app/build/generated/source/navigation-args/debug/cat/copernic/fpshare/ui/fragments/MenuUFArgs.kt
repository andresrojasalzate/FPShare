package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class MenuUFArgs(
  public val cicloId: String,
  public val moduloId: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("cicloId", this.cicloId)
    result.putString("moduloId", this.moduloId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("cicloId", this.cicloId)
    result.set("moduloId", this.moduloId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): MenuUFArgs {
      bundle.setClassLoader(MenuUFArgs::class.java.classLoader)
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
      return MenuUFArgs(__cicloId, __moduloId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): MenuUFArgs {
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
      return MenuUFArgs(__cicloId, __moduloId)
    }
  }
}
