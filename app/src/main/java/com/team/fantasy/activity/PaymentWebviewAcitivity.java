package com.team.fantasy.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.fantasy.R;

public class PaymentWebviewAcitivity extends AppCompatActivity {

    private WebView wv1;
    PaymentWebviewAcitivity activity;
    Context context;
    ImageView im_back;
    TextView tv_HeaderName;
    SwipeRefreshLayout swipeRefreshLayout;
    String IntentHeading,IntentURL,SuccessURL;
    int headerColor,headerTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_webview_acitivity);
        context = activity = this;
        IntentHeading = getIntent().getStringExtra("Heading");
        IntentURL = getIntent().getStringExtra("URL");
        SuccessURL = getIntent().getStringExtra("SuccessURL");
        headerColor = getIntent().getIntExtra("headerColor", R.color.colorPrimary);
        headerTextColor = getIntent().getIntExtra("headerTextColor", R.color.colorPrimary);

        initViews();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        LoadPage(IntentURL);
                                    }
                                }
        );

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadPage(IntentURL);

            }
        });

    }
    public void initViews(){

        im_back = findViewById(R.id.im_back);
        tv_HeaderName = findViewById(R.id.tv_HeaderName);
        wv1= findViewById(R.id.webView1);

        tv_HeaderName.setText(IntentHeading);

        RelativeLayout rlHead = findViewById(R.id.rlhead);
        int backgroundColor = ContextCompat.getColor(this, headerColor);
        int textColor = ContextCompat.getColor(this, headerTextColor);
        rlHead.setBackgroundColor(backgroundColor);
        tv_HeaderName.setTextColor(textColor);
        im_back.setColorFilter(textColor);

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void LoadPage(String Url){
        wv1.setWebViewClient(new MyBrowser());
        wv1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }
        });
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(Url);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (url.contains(SuccessURL)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Start an intent to navigate to MyAccountActivity
                        Intent intent = new Intent(PaymentWebviewAcitivity.this, MyAccountActivity.class);
                        startActivity(intent);

                        finish();
                    }
                }, 7000);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        // Show a confirmation dialog before leaving the activity
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to leave the payment portal?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Finish the activity if the user confirms
                dialogInterface.dismiss();  // Dismiss the dialog first
                Intent redirect = new Intent(PaymentWebviewAcitivity.this, MyAccountActivity.class);
                startActivity(redirect);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the dialog if the user cancels
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
