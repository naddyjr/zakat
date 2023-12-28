package com.somnest.zakaat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somnest.zakaat.Adapter.ContentAdapter;
import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.Model.ContentModel;
import com.somnest.zakaat.Model.HajjModel;
import com.somnest.zakaat.Util.Util;
import java.util.ArrayList;
import java.util.List;


public class HajjDetailActivity extends AppCompatActivity {
    LinearLayout btnMap;
    ImageView ivThumbnail;
    RelativeLayout layout;
    RecyclerView rvContent;
    TextView tvDate;
    TextView tvSubTitle;
    TextView tvTitle;
    HajjModel hajjModel = new HajjModel();
    List<ContentModel> contentModelList = new ArrayList();
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_hajj_detail);




        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
        this.tvDate = (TextView) findViewById(R.id.tvDate);
        this.ivThumbnail = (ImageView) findViewById(R.id.ivThumbnail);
        this.btnMap = (LinearLayout) findViewById(R.id.btnMap);
        this.rvContent = (RecyclerView) findViewById(R.id.rvContent);
        HajjModel hajjModel = (HajjModel) getIntent().getSerializableExtra("hajjModel");
        this.hajjModel = hajjModel;
        this.tvTitle.setText(hajjModel.getTitle());
        this.tvSubTitle.setText(this.hajjModel.getSubTitle());
        if (!this.hajjModel.getDate().isEmpty()) {
            this.tvDate.setText(this.hajjModel.getDate());
        } else {
            this.tvDate.setVisibility(View.GONE);
        }
        this.ivThumbnail.setImageDrawable(Util.imgToDraw(this, this.hajjModel.getImgName()));
        if (!this.hajjModel.getDescription().isEmpty()) {
            setContentModel("", this.hajjModel.getDescription());
        }
        if (!this.hajjModel.getHistory().isEmpty()) {
            setContentModel("History", this.hajjModel.getHistory());
        }
        if (!this.hajjModel.getArchitecture().isEmpty()) {
            setContentModel("Architecture", this.hajjModel.getArchitecture());
        }
        this.rvContent.setLayoutManager(new LinearLayoutManager(this));
        this.rvContent.setAdapter(new ContentAdapter(this, this.contentModelList));
        if (this.hajjModel.getLatitude().isEmpty() || this.hajjModel.getLongitude().isEmpty()) {
            this.btnMap.setVisibility(View.GONE);
        }




        this.btnMap.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(HajjDetailActivity.this, MapActivity.class);
                intent.putExtra("lat", HajjDetailActivity.this.hajjModel.getLatitude());
                intent.putExtra("lang", HajjDetailActivity.this.hajjModel.getLongitude());
                HajjDetailActivity.this.startActivity(intent);
            }
        });
    }

    public void setContentModel(String str, String str2) {
        ContentModel contentModel = new ContentModel();
        contentModel.setCategory(str);
        contentModel.setContent(str2);
        this.contentModelList.add(contentModel);
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
