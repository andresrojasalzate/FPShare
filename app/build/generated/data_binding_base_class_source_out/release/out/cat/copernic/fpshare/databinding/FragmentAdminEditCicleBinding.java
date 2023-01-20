// Generated by view binder compiler. Do not edit!
package cat.copernic.fpshare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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

public final class FragmentAdminEditCicleBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout-sw600dp/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   */
  @Nullable
  public final Button btnDeleteCicle;

  @NonNull
  public final Button btnSaveEdit;

  @NonNull
  public final TextInputEditText inputEditCicle;

  @NonNull
  public final TextInputLayout inputMainEditNameCicle;

  private FragmentAdminEditCicleBinding(@NonNull FrameLayout rootView,
      @Nullable Button btnDeleteCicle, @NonNull Button btnSaveEdit,
      @NonNull TextInputEditText inputEditCicle, @NonNull TextInputLayout inputMainEditNameCicle) {
    this.rootView = rootView;
    this.btnDeleteCicle = btnDeleteCicle;
    this.btnSaveEdit = btnSaveEdit;
    this.inputEditCicle = inputEditCicle;
    this.inputMainEditNameCicle = inputMainEditNameCicle;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAdminEditCicleBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAdminEditCicleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_admin_edit_cicle, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAdminEditCicleBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnDeleteCicle;
      Button btnDeleteCicle = ViewBindings.findChildViewById(rootView, id);

      id = R.id.btnSaveEdit;
      Button btnSaveEdit = ViewBindings.findChildViewById(rootView, id);
      if (btnSaveEdit == null) {
        break missingId;
      }

      id = R.id.inputEditCicle;
      TextInputEditText inputEditCicle = ViewBindings.findChildViewById(rootView, id);
      if (inputEditCicle == null) {
        break missingId;
      }

      id = R.id.inputMainEditNameCicle;
      TextInputLayout inputMainEditNameCicle = ViewBindings.findChildViewById(rootView, id);
      if (inputMainEditNameCicle == null) {
        break missingId;
      }

      return new FragmentAdminEditCicleBinding((FrameLayout) rootView, btnDeleteCicle, btnSaveEdit,
          inputEditCicle, inputMainEditNameCicle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
