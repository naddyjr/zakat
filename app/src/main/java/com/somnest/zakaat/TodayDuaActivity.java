package com.somnest.zakaat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.DatabaseHelper.DatabaseAccess;
import com.somnest.zakaat.Model.DuaModel;
import com.somnest.zakaat.Service.PrayerTimeService;
import com.somnest.zakaat.Util.Util;


public class TodayDuaActivity extends AppCompatActivity {
    DatabaseAccess databaseAccess;
    DuaModel duaModel;
    int id;
    ImageView ivFavorite;
    ImageView ivShare;
    TextView tvDua;
    TextView tvEnglishMeaning;
    TextView tvReference;
    TextView tvTranslation;

    @Override 
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_today_dua);




        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.tvDua = (TextView) findViewById(R.id.tvDua);
        this.tvTranslation = (TextView) findViewById(R.id.tvTranslation);
        this.tvEnglishMeaning = (TextView) findViewById(R.id.tvEnglishMeaning);
        this.tvReference = (TextView) findViewById(R.id.tvReference);
        this.ivFavorite = (ImageView) findViewById(R.id.ivFavorite);
        this.ivShare = (ImageView) findViewById(R.id.ivShare);
        this.id = PrayerTimeService.duaId;
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        this.databaseAccess = databaseAccess;
        DuaModel dua = databaseAccess.getDua(this.id);
        this.duaModel = dua;
        this.tvDua.setText(dua.getDua());
        this.tvTranslation.setText(this.duaModel.getTranslation());
        this.tvEnglishMeaning.setText(this.duaModel.getEnMeaning());
        this.tvReference.setText(this.duaModel.getEnReference());
        if (this.duaModel.isFavorite()) {
            this.ivFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            this.ivFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
        this.ivFavorite.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (TodayDuaActivity.this.duaModel.isFavorite()) {
                    TodayDuaActivity.this.duaModel.setFavorite(false);
                    TodayDuaActivity.this.ivFavorite.setImageResource(R.drawable.ic_favorite_border);
                } else {
                    TodayDuaActivity.this.duaModel.setFavorite(true);
                    TodayDuaActivity.this.ivFavorite.setImageResource(R.drawable.ic_favorite);
                }
                TodayDuaActivity.this.databaseAccess.updateFavoriteDua(TodayDuaActivity.this.duaModel);
            }
        });
        this.ivShare.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Util.shareText(TodayDuaActivity.this, TodayDuaActivity.this.duaModel.getDua() + "\n\n" + TodayDuaActivity.this.duaModel.getTranslation() + "\n\n" + TodayDuaActivity.this.duaModel.getEnMeaning());
            }
        });
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
