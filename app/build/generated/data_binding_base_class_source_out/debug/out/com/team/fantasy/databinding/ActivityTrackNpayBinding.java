// Generated by data binding compiler. Do not edit!
package com.team.fantasy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.team.fantasy.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityTrackNpayBinding extends ViewDataBinding {
  @NonNull
  public final EditText etCheckCity;

  @NonNull
  public final EditText etCheckEmail;

  @NonNull
  public final EditText etCheckName;

  @NonNull
  public final EditText etCheckNumber;

  @NonNull
  public final EditText etCheckState;

  @NonNull
  public final EditText etCheckZipCode;

  @NonNull
  public final ActivityMainheaderBinding head;

  @NonNull
  public final TextView tvCheckout;

  @NonNull
  public final TextView tvTotalamount;

  @NonNull
  public final TextView tvTotalamountHead;

  protected ActivityTrackNpayBinding(Object _bindingComponent, View _root, int _localFieldCount,
      EditText etCheckCity, EditText etCheckEmail, EditText etCheckName, EditText etCheckNumber,
      EditText etCheckState, EditText etCheckZipCode, ActivityMainheaderBinding head,
      TextView tvCheckout, TextView tvTotalamount, TextView tvTotalamountHead) {
    super(_bindingComponent, _root, _localFieldCount);
    this.etCheckCity = etCheckCity;
    this.etCheckEmail = etCheckEmail;
    this.etCheckName = etCheckName;
    this.etCheckNumber = etCheckNumber;
    this.etCheckState = etCheckState;
    this.etCheckZipCode = etCheckZipCode;
    this.head = head;
    this.tvCheckout = tvCheckout;
    this.tvTotalamount = tvTotalamount;
    this.tvTotalamountHead = tvTotalamountHead;
  }

  @NonNull
  public static ActivityTrackNpayBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_track_npay, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityTrackNpayBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityTrackNpayBinding>inflateInternal(inflater, R.layout.activity_track_npay, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityTrackNpayBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_track_npay, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityTrackNpayBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityTrackNpayBinding>inflateInternal(inflater, R.layout.activity_track_npay, null, false, component);
  }

  public static ActivityTrackNpayBinding bind(@NonNull View view) {
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
  public static ActivityTrackNpayBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityTrackNpayBinding)bind(component, view, R.layout.activity_track_npay);
  }
}
