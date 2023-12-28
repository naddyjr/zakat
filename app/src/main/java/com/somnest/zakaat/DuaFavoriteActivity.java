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

import com.somnest.zakaat.Adapter.DuaAdapter;
import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.DatabaseHelper.DatabaseAccess;
import com.somnest.zakaat.Model.DuaModel;
import com.somnest.zakaat.OnItemClickListener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class DuaFavoriteActivity extends AppCompatActivity implements OnItemClickListener {
    DatabaseAccess databaseAccess;
    List<DuaModel> duaModelList = new ArrayList();
    RelativeLayout layout;
    TextView llNotice;
    RecyclerView rvFavoriteDua;

    @Override 
    public void OnClick(View view, int i) {
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dua_favorite);





        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.llNotice = (TextView) findViewById(R.id.llNotice);
        this.rvFavoriteDua = (RecyclerView) findViewById(R.id.rvFavoriteDua);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        this.databaseAccess = databaseAccess;
        databaseAccess.open();
        List<DuaModel> favoriteDua = this.databaseAccess.getFavoriteDua();
        this.duaModelList = favoriteDua;
        if (favoriteDua.isEmpty()) {
            this.llNotice.setVisibility(View.VISIBLE);
            this.rvFavoriteDua.setVisibility(View.GONE);
        } else {
            this.llNotice.setVisibility(View.GONE);
            this.rvFavoriteDua.setVisibility(View.VISIBLE);
        }
        this.rvFavoriteDua.setLayoutManager(new LinearLayoutManager(this));
        this.rvFavoriteDua.setAdapter(new DuaAdapter(this, this.duaModelList, this));
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
