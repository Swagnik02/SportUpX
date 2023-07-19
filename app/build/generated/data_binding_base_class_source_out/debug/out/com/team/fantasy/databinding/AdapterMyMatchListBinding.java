// Generated by data binding compiler. Do not edit!
package com.team.fantasy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.team.fantasy.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class AdapterMyMatchListBinding extends ViewDataBinding {
  @NonNull
  public final RelativeLayout RLMyMatchListItem;

  @NonNull
  public final LinearLayout bottomLayout;

  @NonNull
  public final CircularImageView imTeam1;

  @NonNull
  public final CircularImageView imTeam2;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final LinearLayout linearlayout2;

  @NonNull
  public final RelativeLayout mainLayout;

  @NonNull
  public final ImageView megaphone;

  @NonNull
  public final View team1ColoredBg;

  @NonNull
  public final View team2ColoredBg;

  @NonNull
  public final TextView teamOneFullName;

  @NonNull
  public final TextView teamTwoFullName;

  @NonNull
  public final TextView tvHomeLineUpOut;

  @NonNull
  public final TextView tvJoinedContestCount;

  @NonNull
  public final TextView tvMatchResult;

  @NonNull
  public final TextView tvMatchTime;

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
  public final TextView tvTeamsName;

  @NonNull
  public final TextView tvTimeRemained;

  protected AdapterMyMatchListBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RelativeLayout RLMyMatchListItem, LinearLayout bottomLayout, CircularImageView imTeam1,
      CircularImageView imTeam2, LinearLayout linearLayout, LinearLayout linearlayout2,
      RelativeLayout mainLayout, ImageView megaphone, View team1ColoredBg, View team2ColoredBg,
      TextView teamOneFullName, TextView teamTwoFullName, TextView tvHomeLineUpOut,
      TextView tvJoinedContestCount, TextView tvMatchResult, TextView tvMatchTime,
      TextView tvTeamOneName, TextView tvTeamOneOver, TextView tvTeamOneScore,
      TextView tvTeamTwoName, TextView tvTeamTwoOver, TextView tvTeamTwoScore, TextView tvTeamsName,
      TextView tvTimeRemained) {
    super(_bindingComponent, _root, _localFieldCount);
    this.RLMyMatchListItem = RLMyMatchListItem;
    this.bottomLayout = bottomLayout;
    this.imTeam1 = imTeam1;
    this.imTeam2 = imTeam2;
    this.linearLayout = linearLayout;
    this.linearlayout2 = linearlayout2;
    this.mainLayout = mainLayout;
    this.megaphone = megaphone;
    this.team1ColoredBg = team1ColoredBg;
    this.team2ColoredBg = team2ColoredBg;
    this.teamOneFullName = teamOneFullName;
    this.teamTwoFullName = teamTwoFullName;
    this.tvHomeLineUpOut = tvHomeLineUpOut;
    this.tvJoinedContestCount = tvJoinedContestCount;
    this.tvMatchResult = tvMatchResult;
    this.tvMatchTime = tvMatchTime;
    this.tvTeamOneName = tvTeamOneName;
    this.tvTeamOneOver = tvTeamOneOver;
    this.tvTeamOneScore = tvTeamOneScore;
    this.tvTeamTwoName = tvTeamTwoName;
    this.tvTeamTwoOver = tvTeamTwoOver;
    this.tvTeamTwoScore = tvTeamTwoScore;
    this.tvTeamsName = tvTeamsName;
    this.tvTimeRemained = tvTimeRemained;
  }

  @NonNull
  public static AdapterMyMatchListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.adapter_my_match_list, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static AdapterMyMatchListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<AdapterMyMatchListBinding>inflateInternal(inflater, R.layout.adapter_my_match_list, root, attachToRoot, component);
  }

  @NonNull
  public static AdapterMyMatchListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.adapter_my_match_list, null, false, component)
   */
  @NonNull
  @Deprecated
  public static AdapterMyMatchListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<AdapterMyMatchListBinding>inflateInternal(inflater, R.layout.adapter_my_match_list, null, false, component);
  }

  public static AdapterMyMatchListBinding bind(@NonNull View view) {
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
  public static AdapterMyMatchListBinding bind(@NonNull View view, @Nullable Object component) {
    return (AdapterMyMatchListBinding)bind(component, view, R.layout.adapter_my_match_list);
  }
}
