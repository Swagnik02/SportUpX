// Generated by data binding compiler. Do not edit!
package com.team.fantasy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.team.fantasy.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemWinningBreakupBinding extends ViewDataBinding {
  @NonNull
  public final TextView tvPrice;

  @NonNull
  public final TextView tvRank;

  protected ItemWinningBreakupBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView tvPrice, TextView tvRank) {
    super(_bindingComponent, _root, _localFieldCount);
    this.tvPrice = tvPrice;
    this.tvRank = tvRank;
  }

  @NonNull
  public static ItemWinningBreakupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_winning_breakup, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemWinningBreakupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemWinningBreakupBinding>inflateInternal(inflater, R.layout.item_winning_breakup, root, attachToRoot, component);
  }

  @NonNull
  public static ItemWinningBreakupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_winning_breakup, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemWinningBreakupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemWinningBreakupBinding>inflateInternal(inflater, R.layout.item_winning_breakup, null, false, component);
  }

  public static ItemWinningBreakupBinding bind(@NonNull View view) {
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
  public static ItemWinningBreakupBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemWinningBreakupBinding)bind(component, view, R.layout.item_winning_breakup);
  }
}
