// Generated by view binder compiler. Do not edit!
package cat.copernic.fpshare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import cat.copernic.fpshare.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRecoveryPasswordBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnRecovery;

  @NonNull
  public final TextInputEditText editTextRecovery;

  @NonNull
  public final TextView emailTextviewRecoverypassword;

  @NonNull
  public final TextInputLayout inputEmailRecovery;

  @NonNull
  public final ImageView logoRecoveryPassword;

  @NonNull
  public final ConstraintLayout passwordLayout;

  private ActivityRecoveryPasswordBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnRecovery, @NonNull TextInputEditText editTextRecovery,
      @NonNull TextView emailTextviewRecoverypassword, @NonNull TextInputLayout inputEmailRecovery,
      @NonNull ImageView logoRecoveryPassword, @NonNull ConstraintLayout passwordLayout) {
    this.rootView = rootView;
    this.btnRecovery = btnRecovery;
    this.editTextRecovery = editTextRecovery;
    this.emailTextviewRecoverypassword = emailTextviewRecoverypassword;
    this.inputEmailRecovery = inputEmailRecovery;
    this.logoRecoveryPassword = logoRecoveryPassword;
    this.passwordLayout = passwordLayout;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRecoveryPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRecoveryPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_recovery_password, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRecoveryPasswordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_recovery;
      Button btnRecovery = ViewBindings.findChildViewById(rootView, id);
      if (btnRecovery == null) {
        break missingId;
      }

      id = R.id.editText_recovery;
      TextInputEditText editTextRecovery = ViewBindings.findChildViewById(rootView, id);
      if (editTextRecovery == null) {
        break missingId;
      }

      id = R.id.email_textview_recoverypassword;
      TextView emailTextviewRecoverypassword = ViewBindings.findChildViewById(rootView, id);
      if (emailTextviewRecoverypassword == null) {
        break missingId;
      }

      id = R.id.inputEmailRecovery;
      TextInputLayout inputEmailRecovery = ViewBindings.findChildViewById(rootView, id);
      if (inputEmailRecovery == null) {
        break missingId;
      }

      id = R.id.logo_recovery_password;
      ImageView logoRecoveryPassword = ViewBindings.findChildViewById(rootView, id);
      if (logoRecoveryPassword == null) {
        break missingId;
      }

      ConstraintLayout passwordLayout = (ConstraintLayout) rootView;

      return new ActivityRecoveryPasswordBinding((ConstraintLayout) rootView, btnRecovery,
          editTextRecovery, emailTextviewRecoverypassword, inputEmailRecovery, logoRecoveryPassword,
          passwordLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
