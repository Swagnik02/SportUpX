package com.team.fantasy.databinding;
import com.team.fantasy.R;
import com.team.fantasy.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityInviteFriendsBindingImpl extends ActivityInviteFriendsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(11);
        sIncludes.setIncludes(1, 
            new String[] {"activity_mainheader"},
            new int[] {2},
            new int[] {com.team.fantasy.R.layout.activity_mainheader});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.materialCardView, 3);
        sViewsWithIds.put(R.id.tv_text_msg3, 4);
        sViewsWithIds.put(R.id.referalCodeLayout, 5);
        sViewsWithIds.put(R.id.tv_UniqueCode, 6);
        sViewsWithIds.put(R.id.tv_InviteFriend_layout, 7);
        sViewsWithIds.put(R.id.tv_InviteFriend, 8);
        sViewsWithIds.put(R.id.tv_MyFriendList_layout, 9);
        sViewsWithIds.put(R.id.tv_MyFriendList, 10);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityInviteFriendsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private ActivityInviteFriendsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.team.fantasy.databinding.ActivityMainheaderBinding) bindings[2]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.TextView) bindings[8]
            , (net.orandja.shadowlayout.ShadowLayout) bindings[7]
            , (android.widget.TextView) bindings[10]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[6]
            );
        setContainedBinding(this.head);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
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