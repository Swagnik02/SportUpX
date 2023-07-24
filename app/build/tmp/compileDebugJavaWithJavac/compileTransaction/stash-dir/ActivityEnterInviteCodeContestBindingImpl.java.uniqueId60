package com.team.fantasy.databinding;
import com.team.fantasy.R;
import com.team.fantasy.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityEnterInviteCodeContestBindingImpl extends ActivityEnterInviteCodeContestBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(9);
        sIncludes.setIncludes(0, 
            new String[] {"activity_mainheader"},
            new int[] {2},
            new int[] {com.team.fantasy.R.layout.activity_mainheader});
        sIncludes.setIncludes(1, 
            new String[] {"adapter_contest_list"},
            new int[] {3},
            new int[] {com.team.fantasy.R.layout.adapter_contest_list});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.head2, 4);
        sViewsWithIds.put(R.id.tv_inviteHead, 5);
        sViewsWithIds.put(R.id.input_InviteCode, 6);
        sViewsWithIds.put(R.id.et_InviteCodeforContest, 7);
        sViewsWithIds.put(R.id.tv_JoinThisContest, 8);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityEnterInviteCodeContestBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }
    private ActivityEnterInviteCodeContestBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.EditText) bindings[7]
            , (com.team.fantasy.databinding.ActivityMainheaderBinding) bindings[2]
            , (android.widget.RelativeLayout) bindings[4]
            , (com.team.fantasy.databinding.AdapterContestListBinding) bindings[3]
            , (com.google.android.material.textfield.TextInputLayout) bindings[6]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[8]
            );
        this.RLContestList.setTag(null);
        setContainedBinding(this.head);
        setContainedBinding(this.inclAdapter);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        head.invalidateAll();
        inclAdapter.invalidateAll();
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
        if (inclAdapter.hasPendingBindings()) {
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
        inclAdapter.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeInclAdapter((com.team.fantasy.databinding.AdapterContestListBinding) object, fieldId);
            case 1 :
                return onChangeHead((com.team.fantasy.databinding.ActivityMainheaderBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeInclAdapter(com.team.fantasy.databinding.AdapterContestListBinding InclAdapter, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeHead(com.team.fantasy.databinding.ActivityMainheaderBinding Head, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
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
        executeBindingsOn(inclAdapter);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): inclAdapter
        flag 1 (0x2L): head
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}