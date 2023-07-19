// Generated by data binding compiler. Do not edit!
package com.team.fantasy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.team.fantasy.R;
import java.lang.Deprecated;
import java.lang.Object;
import net.orandja.shadowlayout.ShadowLayout;

public abstract class ActivityInviteFriendsBinding extends ViewDataBinding {
  @NonNull
  public final ActivityMainheaderBinding head;

  @NonNull
  public final LinearLayout materialCardView;

  @NonNull
  public final LinearLayout referalCodeLayout;

  @NonNull
  public final TextView tvInviteFriend;

  @NonNull
  public final ShadowLayout tvInviteFriendLayout;

  @NonNull
  public final TextView tvMyFriendList;

  @NonNull
  public final LinearLayout tvMyFriendListLayout;

  @NonNull
  public final TextView tvTextMsg3;

  @NonNull
  public final TextView tvUniqueCode;

  protected ActivityInviteFriendsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ActivityMainheaderBinding head, LinearLayout materialCardView, LinearLayout referalCodeLayout,
      TextView tvInviteFriend, ShadowLayout tvInviteFriendLayout, TextView tvMyFriendList,
      LinearLayout tvMyFriendListLayout, TextView tvTextMsg3, TextView tvUniqueCode) {
    super(_bindingComponent, _root, _localFieldCount);
    this.head = head;
    this.materialCardView = materialCardView;
    this.referalCodeLayout = referalCodeLayout;
    this.tvInviteFriend = tvInviteFriend;
    this.tvInviteFriendLayout = tvInviteFriendLayout;
    this.tvMyFriendList = tvMyFriendList;
    this.tvMyFriendListLayout = tvMyFriendListLayout;
    this.tvTextMsg3 = tvTextMsg3;
    this.tvUniqueCode = tvUniqueCode;
  }

  @NonNull
  public static ActivityInviteFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_invite_friends, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityInviteFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityInviteFriendsBinding>inflateInternal(inflater, R.layout.activity_invite_friends, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityInviteFriendsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_invite_friends, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityInviteFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityInviteFriendsBinding>inflateInternal(inflater, R.layout.activity_invite_friends, null, false, component);
  }

  public static ActivityInviteFriendsBinding bind(@NonNull View view) {
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
  public static ActivityInviteFriendsBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityInviteFriendsBinding)bind(component, view, R.layout.activity_invite_friends);
  }
}
