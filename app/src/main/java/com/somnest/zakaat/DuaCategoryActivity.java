package com.somnest.zakaat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somnest.zakaat.Adapter.DuaCategoryAdapter;
import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.DatabaseHelper.DatabaseAccess;
import com.somnest.zakaat.Model.DuaCategoryModel;
import com.somnest.zakaat.OnItemClickListener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;


public class DuaCategoryActivity extends AppCompatActivity implements OnItemClickListener {
    LinearLayout btnFavorite;
    DatabaseAccess databaseAccess;
    DuaCategoryAdapter duaCategoryAdapter;
    List<DuaCategoryModel> duaCategoryModelList = new ArrayList();
    RelativeLayout layout;
    RecyclerView rvDuaCategory;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setContentView(R.layout.activity_dua_category);



        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.databaseAccess = DatabaseAccess.getInstance(this);
        this.btnFavorite = (LinearLayout) findViewById(R.id.btnFavorite);
        this.rvDuaCategory = (RecyclerView) findViewById(R.id.rvDuaCategory);
        this.databaseAccess.open();
        this.duaCategoryModelList = this.databaseAccess.getDuaCategory();
        this.databaseAccess.close();
        this.rvDuaCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.rvDuaCategory.setItemAnimator(new DefaultItemAnimator());
        this.duaCategoryAdapter = new DuaCategoryAdapter(this, this.duaCategoryModelList, this);
          this.rvDuaCategory.setAdapter(duaCategoryAdapter);
        this.btnFavorite.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                DuaCategoryActivity.this.startActivity(new Intent(DuaCategoryActivity.this, DuaFavoriteActivity.class));
            }
        });
    }


    @Override 
    public void OnClick(View view, int i) {
        Intent intent = new Intent(getApplicationContext(), DuaTopicActivity.class);
        intent.putExtra("categoryId", this.duaCategoryModelList.get(i).getId());
        intent.putExtra("categoryName", this.duaCategoryModelList.get(i).getCategoryName());
        intent.putExtra("categoryImage", this.duaCategoryModelList.get(i).getImageName());
        startActivity(intent);
     
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
