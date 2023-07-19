package com.team.fantasy.databinding;
import com.team.fantasy.R;
import com.team.fantasy.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentProfileBindingImpl extends FragmentProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(41);
        sIncludes.setIncludes(0, 
            new String[] {"activity_mainheader"},
            new int[] {1},
            new int[] {com.team.fantasy.R.layout.activity_mainheader});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.im_profilepic, 2);
        sViewsWithIds.put(R.id.tv_ProfileUserName, 3);
        sViewsWithIds.put(R.id.tv_UserLocation, 4);
        sViewsWithIds.put(R.id.RL_Contest, 5);
        sViewsWithIds.put(R.id.tv_JoinedContest, 6);
        sViewsWithIds.put(R.id.RL_Match, 7);
        sViewsWithIds.put(R.id.tv_JoinedMatches, 8);
        sViewsWithIds.put(R.id.RL_Series, 9);
        sViewsWithIds.put(R.id.tv_JoinedSeries, 10);
        sViewsWithIds.put(R.id.RL_Win, 11);
        sViewsWithIds.put(R.id.tv_Wins, 12);
        sViewsWithIds.put(R.id.LL_GlobalRanking, 13);
        sViewsWithIds.put(R.id.Ranking_icon1, 14);
        sViewsWithIds.put(R.id.tv_Ranking_text, 15);
        sViewsWithIds.put(R.id.tv_View_Rank_text, 16);
        sViewsWithIds.put(R.id.tv_see_rank, 17);
        sViewsWithIds.put(R.id.LL_ProfileFriends, 18);
        sViewsWithIds.put(R.id.Friend_icon1, 19);
        sViewsWithIds.put(R.id.tv_MyFriendsList, 20);
        sViewsWithIds.put(R.id.tv_Friend_invite_text, 21);
        sViewsWithIds.put(R.id.tv_Friend_invite_text2, 22);
        sViewsWithIds.put(R.id.tv_InviteFriends, 23);
        sViewsWithIds.put(R.id.LL_Reward, 24);
        sViewsWithIds.put(R.id.Reward_icon1, 25);
        sViewsWithIds.put(R.id.tv_Reward_text, 26);
        sViewsWithIds.put(R.id.tv_View_Reward_text, 27);
        sViewsWithIds.put(R.id.tv_Reward_rank, 28);
        sViewsWithIds.put(R.id.RL_ProfileAccount, 29);
        sViewsWithIds.put(R.id.tv_ProfileAccount, 30);
        sViewsWithIds.put(R.id.tv_ProfileDeposited, 31);
        sViewsWithIds.put(R.id.tv_ProfileWinning, 32);
        sViewsWithIds.put(R.id.tv_ProfileBonus, 33);
        sViewsWithIds.put(R.id.tv_ProfileAddBalance, 34);
        sViewsWithIds.put(R.id.RL_ProfilePersonalDetail, 35);
        sViewsWithIds.put(R.id.tv_ProfileView, 36);
        sViewsWithIds.put(R.id.tv_line, 37);
        sViewsWithIds.put(R.id.tv_ProfileYourMail, 38);
        sViewsWithIds.put(R.id.tv_ProfileChangePassword, 39);
        sViewsWithIds.put(R.id.tv_ProfileLogout, 40);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 41, sIncludes, sViewsWithIds));
    }
    private FragmentProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[19]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[24]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[29]
            , (android.widget.RelativeLayout) bindings[35]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.ImageView) bindings[14]
            , (android.widget.ImageView) bindings[25]
            , (com.team.fantasy.databinding.ActivityMainheaderBinding) bindings[1]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[2]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[37]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[34]
            , (android.widget.TextView) bindings[33]
            , (android.widget.TextView) bindings[39]
            , (android.widget.TextView) bindings[31]
            , (android.widget.TextView) bindings[40]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[36]
            , (android.widget.TextView) bindings[32]
            , (android.widget.TextView) bindings[38]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[28]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[27]
            , (android.widget.TextView) bindings[12]
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