// Generated by data binding compiler. Do not edit!
package com.team.fantasy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.team.fantasy.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityMyResultContestDetailsBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout LLContestTop;

  @NonNull
  public final LinearLayout LLScoreCard;

  @NonNull
  public final RelativeLayout RLLeaderboardList;

  @NonNull
  public final RelativeLayout RLResultMain;

  @NonNull
  public final RecyclerView RvMyResultLeaderboard;

  @NonNull
  public final ActivityMainheaderBinding head;

  @NonNull
  public final LayoutVsBackBinding inclVsBck;

  @NonNull
  public final TextView tvEntryFees;

  @NonNull
  public final TextView tvJoinedWithTeamTop;

  @NonNull
  public final TextView tvStatusNote;

  @NonNull
  public final TextView tvTeamOneName;

  @NonNull
  public final TextView tvTeamOneOver;

  @NonNull
  public final TextView tvTeamOneScore;

  @NonNull
  public final TextView tvTeamTwoName;

  @NonNull
  public final TextView tvTeamTwoOver;

  @NonNull
  public final TextView tvTeamTwoScore;

  @NonNull
  public final TextView tvTotalJoinedTeamCount;

  @NonNull
  public final TextView tvTotalPrice;

  @NonNull
  public final TextView tvWinnersCount;

  protected ActivityMyResultContestDetailsBinding(Object _bindingComponent, View _root,
      int _localFieldCount, LinearLayout LLContestTop, LinearLayout LLScoreCard,
      RelativeLayout RLLeaderboardList, RelativeLayout RLResultMain,
      RecyclerView RvMyResultLeaderboard, ActivityMainheaderBinding head,
      LayoutVsBackBinding inclVsBck, TextView tvEntryFees, TextView tvJoinedWithTeamTop,
      TextView tvStatusNote, TextView tvTeamOneName, TextView tvTeamOneOver,
      TextView tvTeamOneScore, TextView tvTeamTwoName, TextView tvTeamTwoOver,
      TextView tvTeamTwoScore, TextView tvTotalJoinedTeamCount, TextView tvTotalPrice,
      TextView tvWinnersCount) {
    super(_bindingComponent, _root, _localFieldCount);
    this.LLContestTop = LLContestTop;
    this.LLScoreCard = LLScoreCard;
    this.RLLeaderboardList = RLLeaderboardList;
    this.RLResultMain = RLResultMain;
    this.RvMyResultLeaderboard = RvMyResultLeaderboard;
    this.head = head;
    this.inclVsBck = inclVsBck;
    this.tvEntryFees = tvEntryFees;
    this.tvJoinedWithTeamTop = tvJoinedWithTeamTop;
    this.tvStatusNote = tvStatusNote;
    this.tvTeamOneName = tvTeamOneName;
    this.tvTeamOneOver = tvTeamOneOver;
    this.tvTeamOneScore = tvTeamOneScore;
    this.tvTeamTwoName = tvTeamTwoName;
    this.tvTeamTwoOver = tvTeamTwoOver;
    this.tvTeamTwoScore = tvTeamTwoScore;
    this.tvTotalJoinedTeamCount = tvTotalJoinedTeamCount;
    this.tvTotalPrice = tvTotalPrice;
    this.tvWinnersCount = tvWinnersCount;
  }

  @NonNull
  public static ActivityMyResultContestDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_my_result_contest_details, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMyResultContestDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityMyResultContestDetailsBinding>inflateInternal(inflater, R.layout.activity_my_result_contest_details, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityMyResultContestDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_my_result_contest_details, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMyResultContestDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityMyResultContestDetailsBinding>inflateInternal(inflater, R.layout.activity_my_result_contest_details, null, false, component);
  }

  public static ActivityMyResultContestDetailsBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityMyResultContestDetailsBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (ActivityMyResultContestDetailsBinding)bind(component, view, R.layout.activity_my_result_contest_details);
  }
}
