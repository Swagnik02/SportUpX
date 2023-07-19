package com.team.fantasy.databinding;
import com.team.fantasy.R;
import com.team.fantasy.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class AdapterMyJoinedContestListBindingImpl extends AdapterMyJoinedContestListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ddd, 1);
        sViewsWithIds.put(R.id.RL_ContestListMain, 2);
        sViewsWithIds.put(R.id.tv_contestName, 3);
        sViewsWithIds.put(R.id.tv_LiveContestDesc, 4);
        sViewsWithIds.put(R.id.LL_ContestTop, 5);
        sViewsWithIds.put(R.id.tv_TotalPrice, 6);
        sViewsWithIds.put(R.id.tv_WinnersCount, 7);
        sViewsWithIds.put(R.id.RL_ProgressBar, 8);
        sViewsWithIds.put(R.id.PB_EntryProgress, 9);
        sViewsWithIds.put(R.id.tv_TeamLeftCount, 10);
        sViewsWithIds.put(R.id.tv_TotalTeamCount, 11);
        sViewsWithIds.put(R.id.tv_EntryFees, 12);
        sViewsWithIds.put(R.id.tv_MyJoinedTeamCount, 13);
        sViewsWithIds.put(R.id.LL_ShareContest, 14);
        sViewsWithIds.put(R.id.tv_ShareContest, 15);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public AdapterMyJoinedContestListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private AdapterMyJoinedContestListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.ProgressBar) bindings[9]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[7]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}