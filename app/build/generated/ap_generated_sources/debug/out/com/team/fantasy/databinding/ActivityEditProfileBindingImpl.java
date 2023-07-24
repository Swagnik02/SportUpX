package com.team.fantasy.databinding;
import com.team.fantasy.R;
import com.team.fantasy.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityEditProfileBindingImpl extends ActivityEditProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(26);
        sIncludes.setIncludes(0, 
            new String[] {"activity_mainheader"},
            new int[] {1},
            new int[] {com.team.fantasy.R.layout.activity_mainheader});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.profile_image_button, 2);
        sViewsWithIds.put(R.id.input_editName, 3);
        sViewsWithIds.put(R.id.et_EditName, 4);
        sViewsWithIds.put(R.id.input_editEmail, 5);
        sViewsWithIds.put(R.id.et_EditEmail, 6);
        sViewsWithIds.put(R.id.input_editMobile, 7);
        sViewsWithIds.put(R.id.et_EditMobile, 8);
        sViewsWithIds.put(R.id.input_editDob, 9);
        sViewsWithIds.put(R.id.et_EditDob, 10);
        sViewsWithIds.put(R.id.input_editAddress, 11);
        sViewsWithIds.put(R.id.et_EditAddress, 12);
        sViewsWithIds.put(R.id.input_editCity, 13);
        sViewsWithIds.put(R.id.et_EditCity, 14);
        sViewsWithIds.put(R.id.input_editPincode, 15);
        sViewsWithIds.put(R.id.et_EditPincode, 16);
        sViewsWithIds.put(R.id.input_editState, 17);
        sViewsWithIds.put(R.id.et_EditState, 18);
        sViewsWithIds.put(R.id.input_editCountry, 19);
        sViewsWithIds.put(R.id.et_EditCountry, 20);
        sViewsWithIds.put(R.id.tv_EditMale, 21);
        sViewsWithIds.put(R.id.tv_EditFeMale, 22);
        sViewsWithIds.put(R.id.input_editFavouriteTeam, 23);
        sViewsWithIds.put(R.id.et_EditFavouriteTeam, 24);
        sViewsWithIds.put(R.id.tv_EditUpdateProfile, 25);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityEditProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }
    private ActivityEditProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.EditText) bindings[12]
            , (android.widget.EditText) bindings[14]
            , (android.widget.EditText) bindings[20]
            , (android.widget.EditText) bindings[10]
            , (android.widget.EditText) bindings[6]
            , (android.widget.EditText) bindings[24]
            , (android.widget.EditText) bindings[8]
            , (android.widget.EditText) bindings[4]
            , (android.widget.EditText) bindings[16]
            , (android.widget.EditText) bindings[18]
            , (com.team.fantasy.databinding.ActivityMainheaderBinding) bindings[1]
            , (com.google.android.material.textfield.TextInputLayout) bindings[11]
            , (com.google.android.material.textfield.TextInputLayout) bindings[13]
            , (com.google.android.material.textfield.TextInputLayout) bindings[19]
            , (com.google.android.material.textfield.TextInputLayout) bindings[9]
            , (com.google.android.material.textfield.TextInputLayout) bindings[5]
            , (com.google.android.material.textfield.TextInputLayout) bindings[23]
            , (com.google.android.material.textfield.TextInputLayout) bindings[7]
            , (com.google.android.material.textfield.TextInputLayout) bindings[3]
            , (com.google.android.material.textfield.TextInputLayout) bindings[15]
            , (com.google.android.material.textfield.TextInputLayout) bindings[17]
            , (android.widget.ImageButton) bindings[2]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[25]
            );
        setContainedBinding(this.head);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        head.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (head.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        head.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeHead((com.team.fantasy.databinding.ActivityMainheaderBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeHead(com.team.fantasy.databinding.ActivityMainheaderBinding Head, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
        executeBindingsOn(head);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): head
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}