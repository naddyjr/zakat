package com.somnest.zakaat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somnest.zakaat.Adapter.QuranAdapter;
import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.DatabaseHelper.DatabaseAccess;
import com.somnest.zakaat.Model.AlQuranDataModel;
import com.somnest.zakaat.OnItemClickListener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;


public class QuranFavoriteActivity extends AppCompatActivity implements OnItemClickListener {
    List<AlQuranDataModel> alQuranDataModelList = new ArrayList();
    DatabaseAccess databaseAccess;
    RelativeLayout layout;
    TextView llNotice;
    RecyclerView rvFavoriteQuran;

    @Override 
    public void OnClick(View view, int i) {
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_quran_favorite);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.llNotice = (TextView) findViewById(R.id.llNotice);
        this.rvFavoriteQuran = (RecyclerView) findViewById(R.id.rvFavoriteQuran);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        this.databaseAccess = databaseAccess;
        databaseAccess.open();
        List<AlQuranDataModel> favoriteQuran = this.databaseAccess.getFavoriteQuran();
        this.alQuranDataModelList = favoriteQuran;
        if (favoriteQuran.isEmpty()) {
            this.llNotice.setVisibility(View.VISIBLE);
            this.rvFavoriteQuran.setVisibility(View.GONE);
        } else {
            this.llNotice.setVisibility(View.GONE);
            this.rvFavoriteQuran.setVisibility(View.VISIBLE);
        }
        this.rvFavoriteQuran.setLayoutManager(new LinearLayoutManager(this));
        this.rvFavoriteQuran.setAdapter(new QuranAdapter(this, this.alQuranDataModelList, this, 1));
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
