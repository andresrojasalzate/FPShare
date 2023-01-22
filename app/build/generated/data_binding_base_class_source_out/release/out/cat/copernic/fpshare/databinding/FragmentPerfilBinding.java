// Generated by view binder compiler. Do not edit!
package cat.copernic.fpshare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import cat.copernic.fpshare.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPerfilBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final Button buttonSaveChangesProfile;

  @NonNull
  public final TextInputEditText editextEmail;

  @NonNull
  public final TextInputEditText edittextApellidos;

  @NonNull
  public final TextInputEditText edittextInstitute;

  @NonNull
  public final TextInputEditText edittextName;

  @NonNull
  public final TextInputEditText edittextNumero;

  @NonNull
  public final FrameLayout fragmentPerfil;

  @NonNull
  public final ImageView imageProfile;

  @NonNull
  public final TextInputLayout imputEmailProfile;

  @NonNull
  public final TextInputLayout imputInstituteProfile;

  @NonNull
  public final TextInputLayout imputLastnamesProfile;

  @NonNull
  public final TextInputLayout imputNameProfile;

  @NonNull
  public final TextInputLayout imputTelephoneNumberProfile;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout-sw600dp/</li>
   * </ul>
   */
  @Nullable
  public final ProgressBar progressBar;

  @NonNull
  public final TextView textViewEmailProfile;

  @NonNull
  public final TextView textViewInstituteProfile;

  @NonNull
  public final TextView textViewLastnamesProfile;

  @NonNull
  public final TextView textViewNameProfile;

  @NonNull
  public final TextView textViewTelephoneNumberProfile;

  private FragmentPerfilBinding(@NonNull FrameLayout rootView,
      @NonNull Button buttonSaveChangesProfile, @NonNull TextInputEditText editextEmail,
      @NonNull TextInputEditText edittextApellidos, @NonNull TextInputEditText edittextInstitute,
      @NonNull TextInputEditText edittextName, @NonNull TextInputEditText edittextNumero,
      @NonNull FrameLayout fragmentPerfil, @NonNull ImageView imageProfile,
      @NonNull TextInputLayout imputEmailProfile, @NonNull TextInputLayout imputInstituteProfile,
      @NonNull TextInputLayout imputLastnamesProfile, @NonNull TextInputLayout imputNameProfile,
      @NonNull TextInputLayout imputTelephoneNumberProfile, @Nullable ProgressBar progressBar,
      @NonNull TextView textViewEmailProfile, @NonNull TextView textViewInstituteProfile,
      @NonNull TextView textViewLastnamesProfile, @NonNull TextView textViewNameProfile,
      @NonNull TextView textViewTelephoneNumberProfile) {
    this.rootView = rootView;
    this.buttonSaveChangesProfile = buttonSaveChangesProfile;
    this.editextEmail = editextEmail;
    this.edittextApellidos = edittextApellidos;
    this.edittextInstitute = edittextInstitute;
    this.edittextName = edittextName;
    this.edittextNumero = edittextNumero;
    this.fragmentPerfil = fragmentPerfil;
    this.imageProfile = imageProfile;
    this.imputEmailProfile = imputEmailProfile;
    this.imputInstituteProfile = imputInstituteProfile;
    this.imputLastnamesProfile = imputLastnamesProfile;
    this.imputNameProfile = imputNameProfile;
    this.imputTelephoneNumberProfile = imputTelephoneNumberProfile;
    this.progressBar = progressBar;
    this.textViewEmailProfile = textViewEmailProfile;
    this.textViewInstituteProfile = textViewInstituteProfile;
    this.textViewLastnamesProfile = textViewLastnamesProfile;
    this.textViewNameProfile = textViewNameProfile;
    this.textViewTelephoneNumberProfile = textViewTelephoneNumberProfile;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPerfilBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPerfilBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_perfil, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPerfilBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_save_changes_profile;
      Button buttonSaveChangesProfile = ViewBindings.findChildViewById(rootView, id);
      if (buttonSaveChangesProfile == null) {
        break missingId;
      }

      id = R.id.editextEmail;
      TextInputEditText editextEmail = ViewBindings.findChildViewById(rootView, id);
      if (editextEmail == null) {
        break missingId;
      }

      id = R.id.edittext_apellidos;
      TextInputEditText edittextApellidos = ViewBindings.findChildViewById(rootView, id);
      if (edittextApellidos == null) {
        break missingId;
      }

      id = R.id.edittext_institute;
      TextInputEditText edittextInstitute = ViewBindings.findChildViewById(rootView, id);
      if (edittextInstitute == null) {
        break missingId;
      }

      id = R.id.edittext_name;
      TextInputEditText edittextName = ViewBindings.findChildViewById(rootView, id);
      if (edittextName == null) {
        break missingId;
      }

      id = R.id.edittext_numero;
      TextInputEditText edittextNumero = ViewBindings.findChildViewById(rootView, id);
      if (edittextNumero == null) {
        break missingId;
      }

      FrameLayout fragmentPerfil = (FrameLayout) rootView;

      id = R.id.image_profile;
      ImageView imageProfile = ViewBindings.findChildViewById(rootView, id);
      if (imageProfile == null) {
        break missingId;
      }

      id = R.id.imput_email_profile;
      TextInputLayout imputEmailProfile = ViewBindings.findChildViewById(rootView, id);
      if (imputEmailProfile == null) {
        break missingId;
      }

      id = R.id.imput_institute_profile;
      TextInputLayout imputInstituteProfile = ViewBindings.findChildViewById(rootView, id);
      if (imputInstituteProfile == null) {
        break missingId;
      }

      id = R.id.imput_lastnames_profile;
      TextInputLayout imputLastnamesProfile = ViewBindings.findChildViewById(rootView, id);
      if (imputLastnamesProfile == null) {
        break missingId;
      }

      id = R.id.imput_name_profile;
      TextInputLayout imputNameProfile = ViewBindings.findChildViewById(rootView, id);
      if (imputNameProfile == null) {
        break missingId;
      }

      id = R.id.imput_telephone_number_profile;
      TextInputLayout imputTelephoneNumberProfile = ViewBindings.findChildViewById(rootView, id);
      if (imputTelephoneNumberProfile == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);

      id = R.id.textView_email_profile;
      TextView textViewEmailProfile = ViewBindings.findChildViewById(rootView, id);
      if (textViewEmailProfile == null) {
        break missingId;
      }

      id = R.id.textView_institute_profile;
      TextView textViewInstituteProfile = ViewBindings.findChildViewById(rootView, id);
      if (textViewInstituteProfile == null) {
        break missingId;
      }

      id = R.id.textView_lastnames_profile;
      TextView textViewLastnamesProfile = ViewBindings.findChildViewById(rootView, id);
      if (textViewLastnamesProfile == null) {
        break missingId;
      }

      id = R.id.textView_name_profile;
      TextView textViewNameProfile = ViewBindings.findChildViewById(rootView, id);
      if (textViewNameProfile == null) {
        break missingId;
      }

      id = R.id.textView_telephone_number_profile;
      TextView textViewTelephoneNumberProfile = ViewBindings.findChildViewById(rootView, id);
      if (textViewTelephoneNumberProfile == null) {
        break missingId;
      }

      return new FragmentPerfilBinding((FrameLayout) rootView, buttonSaveChangesProfile,
          editextEmail, edittextApellidos, edittextInstitute, edittextName, edittextNumero,
          fragmentPerfil, imageProfile, imputEmailProfile, imputInstituteProfile,
          imputLastnamesProfile, imputNameProfile, imputTelephoneNumberProfile, progressBar,
          textViewEmailProfile, textViewInstituteProfile, textViewLastnamesProfile,
          textViewNameProfile, textViewTelephoneNumberProfile);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}