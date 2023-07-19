// Generated by data binding compiler. Do not edit!
package com.team.fantasy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.team.fantasy.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class AdapterFriendsListBinding extends ViewDataBinding {
  @NonNull
  public final RelativeLayout RLTransMain;

  @NonNull
  public final TextView tvCreditedAmount;

  @NonNull
  public final TextView tvFriendName;

  protected AdapterFriendsListBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RelativeLayout RLTransMain, TextView tvCreditedAmount, TextView tvFriendName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.RLTransMain = RLTransMain;
    this.tvCreditedAmount = tvCreditedAmount;
    this.tvFriendName = tvFriendName;
  }

  @NonNull
  public static AdapterFriendsListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.adapter_friends_list, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static AdapterFriendsListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<AdapterFriendsListBinding>inflateInternal(inflater, R.layout.adapter_friends_list, root, attachToRoot, component);
  }

  @NonNull
  public static AdapterFriendsListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.adapter_friends_list, null, false, component)
   */
  @NonNull
  @Deprecated
  public static AdapterFriendsListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<AdapterFriendsListBinding>inflateInternal(inflater, R.layout.adapter_friends_list, null, false, component);
  }

  public static AdapterFriendsListBinding bind(@NonNull View view) {
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
  public static AdapterFriendsListBinding bind(@NonNull View view, @Nullable Object component) {
    return (AdapterFriendsListBinding)bind(component, view, R.layout.adapter_friends_list);
  }
}
