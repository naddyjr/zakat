package com.somnest.zakaat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somnest.zakaat.Adapter.Allah99NameAdapter;
import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.DatabaseHelper.DatabaseAccess;
import com.somnest.zakaat.Model.AllahNameModel;
import com.somnest.zakaat.OnItemClickListener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;


public class AllahNameActivity extends AppCompatActivity implements OnItemClickListener {
    Allah99NameAdapter allah99NameAdapter;
    List<AllahNameModel> allahNameModelList = new ArrayList();
    DatabaseAccess databaseAccess;
    RelativeLayout layout;
    RecyclerView rv99AllahName;

    @Override 
    public void OnClick(View view, int i) {
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        setContentView(R.layout.activity_99_allah_name);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.databaseAccess = DatabaseAccess.getInstance(this);
        this.rv99AllahName = (RecyclerView) findViewById(R.id.rv99AllahName);
        this.databaseAccess.open();
        this.allahNameModelList = this.databaseAccess.getDua99AllhaName();
        this.databaseAccess.close();
        this.rv99AllahName.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.rv99AllahName.setItemAnimator(new DefaultItemAnimator());
        Allah99NameAdapter allah99NameAdapter = new Allah99NameAdapter(this, this.allahNameModelList, this);
        this.allah99NameAdapter = allah99NameAdapter;
        this.rv99AllahName.setAdapter(allah99NameAdapter);
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
