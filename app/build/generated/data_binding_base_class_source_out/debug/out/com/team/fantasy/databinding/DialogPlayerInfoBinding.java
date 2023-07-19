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
import com.team.fantasy.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogPlayerInfoBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout LLCreditandPoints;

  @NonNull
  public final LinearLayout LLMatchListHead;

  @NonNull
  public final LinearLayout LLPlayerInfo;

  @NonNull
  public final LinearLayout LLPoweredBy;

  @NonNull
  public final LinearLayout LLSeriesPerformanceList;

  @NonNull
  public final RelativeLayout RlDialogHeader;

  @NonNull
  public final ImageView imInfoPlayerImage;

  @NonNull
  public final TextView tvBats;

  @NonNull
  public final TextView tvBowls;

  @NonNull
  public final TextView tvDClose;

  @NonNull
  public final TextView tvInfoCredits;

  @NonNull
  public final TextView tvInfoCrick;

  @NonNull
  public final TextView tvInfoHeadName;

  @NonNull
  public final TextView tvInfoPlayerName;

  @NonNull
  public final TextView tvInfoPoints;

  @NonNull
  public final TextView tvInfoPoweredBy;

  @NonNull
  public final TextView tvNationality;

  @NonNull
  public final TextView tvPlayerDob;

  protected DialogPlayerInfoBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout LLCreditandPoints, LinearLayout LLMatchListHead, LinearLayout LLPlayerInfo,
      LinearLayout LLPoweredBy, LinearLayout LLSeriesPerformanceList, RelativeLayout RlDialogHeader,
      ImageView imInfoPlayerImage, TextView tvBats, TextView tvBowls, TextView tvDClose,
      TextView tvInfoCredits, TextView tvInfoCrick, TextView tvInfoHeadName,
      TextView tvInfoPlayerName, TextView tvInfoPoints, TextView tvInfoPoweredBy,
      TextView tvNationality, TextView tvPlayerDob) {
    super(_bindingComponent, _root, _localFieldCount);
    this.LLCreditandPoints = LLCreditandPoints;
    this.LLMatchListHead = LLMatchListHead;
    this.LLPlayerInfo = LLPlayerInfo;
    this.LLPoweredBy = LLPoweredBy;
    this.LLSeriesPerformanceList = LLSeriesPerformanceList;
    this.RlDialogHeader = RlDialogHeader;
    this.imInfoPlayerImage = imInfoPlayerImage;
    this.tvBats = tvBats;
    this.tvBowls = tvBowls;
    this.tvDClose = tvDClose;
    this.tvInfoCredits = tvInfoCredits;
    this.tvInfoCrick = tvInfoCrick;
    this.tvInfoHeadName = tvInfoHeadName;
    this.tvInfoPlayerName = tvInfoPlayerName;
    this.tvInfoPoints = tvInfoPoints;
    this.tvInfoPoweredBy = tvInfoPoweredBy;
    this.tvNationality = tvNationality;
    this.tvPlayerDob = tvPlayerDob;
  }

  @NonNull
  public static DialogPlayerInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_player_info, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogPlayerInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogPlayerInfoBinding>inflateInternal(inflater, R.layout.dialog_player_info, root, attachToRoot, component);
  }

  @NonNull
  public static DialogPlayerInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_player_info, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogPlayerInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogPlayerInfoBinding>inflateInternal(inflater, R.layout.dialog_player_info, null, false, component);
  }

  public static DialogPlayerInfoBinding bind(@NonNull View view) {
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
  public static DialogPlayerInfoBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogPlayerInfoBinding)bind(component, view, R.layout.dialog_player_info);
  }
}
