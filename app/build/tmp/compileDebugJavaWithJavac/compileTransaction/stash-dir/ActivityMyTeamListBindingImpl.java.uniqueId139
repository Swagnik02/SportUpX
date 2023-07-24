package com.team.fantasy.databinding;
import com.team.fantasy.R;
import com.team.fantasy.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMyTeamListBindingImpl extends ActivityMyTeamListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(8);
        sIncludes.setIncludes(0, 
            new String[] {"activity_mainheader", "layout_vs_back"},
            new int[] {1, 2},
            new int[] {com.team.fantasy.R.layout.activity_mainheader,
                com.team.fantasy.R.layout.layout_vs_back});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.swipeRefreshLayout, 3);
        sViewsWithIds.put(R.id.Rv_MyTeamList, 4);
        sViewsWithIds.put(R.id.tv_NoDataAvailable, 5);
        sViewsWithIds.put(R.id.RL_BottomCreateTeam, 6);
        sViewsWithIds.put(R.id.tv_MyCreateTeam, 7);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMyTeamListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private ActivityMyTeamListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.RelativeLayout) bindings[6]
            , (androidx.recyclerview.widget.RecyclerView) bindings[4]
            , (com.team.fantasy.databinding.ActivityMainheaderBinding) bindings[1]
            , (com.team.fantasy.databinding.LayoutVsBackBinding) bindings[2]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[3]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[5]
            );
        setContainedBinding(this.head);
        setContainedBinding(this.inclVsBck);
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
        inclVsBck.invalidateAll();
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
        if (inclVsBck.hasPendingBindings()) {
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
        inclVsBck.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeInclVsBck((com.team.fantasy.databinding.LayoutVsBackBinding) object, fieldId);
            case 1 :
                return onChangeHead((com.team.fantasy.databinding.ActivityMainheaderBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeInclVsBck(com.team.fantasy.databinding.LayoutVsBackBinding InclVsBck, int fieldId) {
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
        executeBindingsOn(inclVsBck);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): inclVsBck
        flag 1 (0x2L): head
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}