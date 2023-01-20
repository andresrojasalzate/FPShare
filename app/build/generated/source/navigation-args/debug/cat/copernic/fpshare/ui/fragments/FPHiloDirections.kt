package cat.copernic.fpshare.ui.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import cat.copernic.fpshare.R

public class FPHiloDirections private constructor() {
  public companion object {
    public fun actionFPHiloToTusForos(): NavDirections =
        ActionOnlyNavDirections(R.id.action_FPHilo_to_tusForos)
  }
}
