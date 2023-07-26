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
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(25);
        sIncludes.setIncludes(0, 
            new String[] {"activity_mainheader"},
            new int[] {1},
            new int[] {com.team.fantasy.R.layout.activity_mainheader});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.input_editName, 2);
        sViewsWithIds.put(R.id.et_EditName, 3);
        sViewsWithIds.put(R.id.input_editEmail, 4);
        sViewsWithIds.put(R.id.et_EditEmail, 5);
        sViewsWithIds.put(R.id.input_editMobile, 6);
        sViewsWithIds.put(R.id.et_EditMobile, 7);
        sViewsWithIds.put(R.id.input_editFavouriteTeam, 8);
        sViewsWithIds.put(R.id.et_EditFavouriteTeam, 9);
        sViewsWithIds.put(R.id.input_editDob, 10);
        sViewsWithIds.put(R.id.et_EditDob, 11);
        sViewsWithIds.put(R.id.input_editAddress, 12);
        sViewsWithIds.put(R.id.et_EditAddress, 13);
        sViewsWithIds.put(R.id.input_editState, 14);
        sViewsWithIds.put(R.id.et_EditState, 15);
        sViewsWithIds.put(R.id.input_editCity, 16);
        sViewsWithIds.put(R.id.et_EditCity, 17);
        sViewsWithIds.put(R.id.input_editPincode, 18);
        sViewsWithIds.put(R.id.et_EditPincode, 19);
        sViewsWithIds.put(R.id.input_editCountry, 20);
        sViewsWithIds.put(R.id.et_EditCountry, 21);
        sViewsWithIds.put(R.id.tv_EditMale, 22);
        sViewsWithIds.put(R.id.tv_EditFeMale, 23);
        sViewsWithIds.put(R.id.tv_EditUpdateProfile, 24);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityEditProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 25, sIncludes, sViewsWithIds));
    }
    private ActivityEditProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.EditText) bindings[13]
            , (android.widget.EditText) bindings[17]
            , (android.widget.EditText) bindings[21]
            , (android.widget.EditText) bindings[11]
            , (android.widget.EditText) bindings[5]
            , (android.widget.EditText) bindings[9]
            , (android.widget.EditText) bindings[7]
            , (android.widget.EditText) bindings[3]
            , (android.widget.EditText) bindings[19]
            , (android.widget.EditText) bindings[15]
            , (com.team.fantasy.databinding.ActivityMainheaderBinding) bindings[1]
            , (com.google.android.material.textfield.TextInputLayout) bindings[12]
            , (com.google.android.material.textfield.TextInputLayout) bindings[16]
            , (com.google.android.material.textfield.TextInputLayout) bindings[20]
            , (com.google.android.material.textfield.TextInputLayout) bindings[10]
            , (com.google.android.material.textfield.TextInputLayout) bindings[4]
            , (com.google.android.material.textfield.TextInputLayout) bindings[8]
            , (com.google.android.material.textfield.TextInputLayout) bindings[6]
            , (com.google.android.material.textfield.TextInputLayout) bindings[2]
            , (com.google.android.material.textfield.TextInputLayout) bindings[18]
            , (com.google.android.material.textfield.TextInputLayout) bindings[14]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[24]
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