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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somnest.zakaat.Adapter.QuranAdapter;
import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.DatabaseHelper.DatabaseAccess;
import com.somnest.zakaat.Model.AlQuranDataModel;
import com.somnest.zakaat.OnItemClickListener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;


public class QuranActivity extends AppCompatActivity implements OnItemClickListener {
    DatabaseAccess databaseAccess;
    int id;
    RelativeLayout layout;
    QuranAdapter quranAdapter;
    RecyclerView rvQuranData;
    AppCompatTextView toolbar_text;
    List<AlQuranDataModel> alQuranDataModelList = new ArrayList();
    String quranName = "";

    @Override 
    public void OnClick(View view, int i) {
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_quran);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.databaseAccess = DatabaseAccess.getInstance(this);
        this.id = getIntent().getIntExtra("quranId", 0);
        this.quranName = getIntent().getStringExtra("quranName");
        AppCompatTextView appCompatTextView = (AppCompatTextView) findViewById(R.id.toolbar_text);
        this.toolbar_text = appCompatTextView;
        appCompatTextView.setText(this.quranName);
        this.rvQuranData = (RecyclerView) findViewById(R.id.rvQuranData);
        this.databaseAccess.open();
        this.alQuranDataModelList = this.databaseAccess.getAlQuranData(this.id);
        this.databaseAccess.close();
        System.out.println(this.alQuranDataModelList.size());
        this.rvQuranData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.rvQuranData.setItemAnimator(new DefaultItemAnimator());
        QuranAdapter quranAdapter = new QuranAdapter(this, this.alQuranDataModelList, this, 0);
        this.quranAdapter = quranAdapter;
        this.rvQuranData.setAdapter(quranAdapter);
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
