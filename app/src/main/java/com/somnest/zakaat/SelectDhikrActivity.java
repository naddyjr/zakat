package com.somnest.zakaat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somnest.zakaat.Adapter.DhikrAdapter;
import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.DatabaseHelper.DatabaseAccess;
import com.somnest.zakaat.Model.DhikrModel;
import com.somnest.zakaat.OnItemClickListener.OnItemClickListener;
import com.somnest.zakaat.PreferenceUtil.PrayerSharedPreference;
import java.util.ArrayList;
import java.util.List;


public class SelectDhikrActivity extends AppCompatActivity implements OnItemClickListener {
    DatabaseAccess databaseAccess;
    DhikrAdapter dhikrAdapter;
    List<DhikrModel> dhikrModelList = new ArrayList();
    RelativeLayout layout;
    PrayerSharedPreference prayerSharedPreference;
    RecyclerView rvDhikr;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_select_dhikr);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.prayerSharedPreference = new PrayerSharedPreference(this);
        this.databaseAccess = DatabaseAccess.getInstance(this);
        this.rvDhikr = (RecyclerView) findViewById(R.id.rvDhikr);
        this.databaseAccess.open();
        this.dhikrModelList = this.databaseAccess.getAllDhikr();
        this.databaseAccess.close();
        this.rvDhikr.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.rvDhikr.setItemAnimator(new DefaultItemAnimator());
        DhikrAdapter dhikrAdapter = new DhikrAdapter(this, this.dhikrModelList, this);
        this.dhikrAdapter = dhikrAdapter;
        this.rvDhikr.setAdapter(dhikrAdapter);
    }

    @Override 
    public void OnClick(View view, int i) {
        this.prayerSharedPreference.setDhikrId(this.dhikrModelList.get(i).getId());
        finish();
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
