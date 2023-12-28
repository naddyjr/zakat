package com.somnest.zakaat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somnest.zakaat.Adapter.QuranCategoryAdapter;
import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.DatabaseHelper.DatabaseAccess;
import com.somnest.zakaat.Model.AlQuranCategoryModel;
import com.somnest.zakaat.OnItemClickListener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;


public class QuranCategoryActivity extends AppCompatActivity implements OnItemClickListener {
    QuranCategoryAdapter alQuranCategoryAdapter;
    List<AlQuranCategoryModel> alQuranCategoryModelList = new ArrayList();
    LinearLayout btnFavorite;
    DatabaseAccess databaseAccess;
    RelativeLayout layout;
    RecyclerView rvAlQuranCategory;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        setContentView(R.layout.activity_quran_category);




        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.databaseAccess = DatabaseAccess.getInstance(this);
        this.btnFavorite = (LinearLayout) findViewById(R.id.btnFavorite);
        this.rvAlQuranCategory = (RecyclerView) findViewById(R.id.rvAlQuranCategory);
        this.databaseAccess.open();
        this.alQuranCategoryModelList = this.databaseAccess.getAlQuranCategory();
        this.databaseAccess.close();
        this.rvAlQuranCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.rvAlQuranCategory.setItemAnimator(new DefaultItemAnimator());
        QuranCategoryAdapter quranCategoryAdapter = new QuranCategoryAdapter(this, this.alQuranCategoryModelList, this);
        this.alQuranCategoryAdapter = quranCategoryAdapter;
        this.rvAlQuranCategory.setAdapter(quranCategoryAdapter);
        this.btnFavorite.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                QuranCategoryActivity.this.startActivity(new Intent(QuranCategoryActivity.this, QuranFavoriteActivity.class));

            }
        });
    }

    @Override 
    public void OnClick(View view, int i) {
        Intent intent = new Intent(this, QuranActivity.class);
        intent.putExtra("quranId", this.alQuranCategoryModelList.get(i).getId());
        intent.putExtra("quranName", this.alQuranCategoryModelList.get(i).getArabicTranslation());
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
