package com.somnest.zakaat.Appcompany;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.somnest.zakaat.R;


public class Privacy_Policy_activity extends AppCompatActivity {
    private static final String TAG = "Main";
    Context context;

    private ProgressDialog progress;
    Toolbar toolbar;
    private WebView webvw;
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.privacy_policy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        this.toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setTitle("Download more apps");
        this.context = this;
        this.webvw = (WebView) findViewById(R.id.webview);
        this.webvw.getSettings().setJavaScriptEnabled(true);
        this.webvw.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        final AlertDialog create = new AlertDialog.Builder(this).create();
        this.progress = ProgressDialog.show(this, "Please Wait...", "Loading...");
        this.webvw.setWebViewClient(new WebViewClient() { 
            @Override 
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                Log.i(Privacy_Policy_activity.TAG, "Processing webview url click...");
                webView.loadUrl(str);
                return true;
            }

            @Override 
            public void onPageFinished(WebView webView, String str) {
                Log.i(Privacy_Policy_activity.TAG, "Finished loading URL: " + str);
                if (Privacy_Policy_activity.this.progress.isShowing()) {
                    Privacy_Policy_activity.this.progress.dismiss();
                }
            }

            @Override 
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                Log.e(Privacy_Policy_activity.TAG, "Error: " + str);
                create.setTitle("Error");
                create.setMessage(str);
                create.setButton("OK", new DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialogInterface, int i2) {
                    }
                });
                create.show();
            }
        });
        this.webvw.loadUrl("https://play.google.com/store/apps/dev?id=8156183671650115880");
    }

    

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
