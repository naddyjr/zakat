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

import com.somnest.zakaat.Adapter.DuaTopicAdapter;
import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.DatabaseHelper.DatabaseAccess;
import com.somnest.zakaat.Model.DuaTopicModel;
import com.somnest.zakaat.OnItemClickListener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;


public class DuaTopicActivity extends AppCompatActivity implements OnItemClickListener {
    String categoryImage;
    DatabaseAccess databaseAccess;
    DuaTopicAdapter duaTopicAdapter;
    RelativeLayout layout;
    RecyclerView rvDuaTopic;
    AppCompatTextView toolbar_text;
    int categoryId = 0;
    List<Integer> getCategoryTopicIds = new ArrayList();
    List<DuaTopicModel> duaTopicModelList = new ArrayList();
    String categoryName = "";

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dua_topic);





        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.databaseAccess = DatabaseAccess.getInstance(this);
        this.categoryId = getIntent().getIntExtra("categoryId", 0);
        this.categoryName = getIntent().getStringExtra("categoryName");
        this.categoryImage = getIntent().getStringExtra("categoryImage");
        this.rvDuaTopic = (RecyclerView) findViewById(R.id.rvDuaTopic);
        AppCompatTextView appCompatTextView = (AppCompatTextView) findViewById(R.id.toolbar_text);
        this.toolbar_text = appCompatTextView;
        appCompatTextView.setText(this.categoryName);
        this.databaseAccess.open();
        this.getCategoryTopicIds = this.databaseAccess.getDuaCategoryTopic(this.categoryId);
        this.databaseAccess.close();
        this.databaseAccess.open();
        for (int i = 0; i < this.getCategoryTopicIds.size(); i++) {
            DuaTopicModel duaTopic = this.databaseAccess.getDuaTopic(this.getCategoryTopicIds.get(i).intValue());
            DuaTopicModel duaTopicModel = new DuaTopicModel();
            duaTopicModel.setId(duaTopic.getId());
            duaTopicModel.setTopicName(duaTopic.getTopicName());
            duaTopicModel.setImageFilePath(this.categoryImage);
            this.duaTopicModelList.add(duaTopicModel);
        }
        this.databaseAccess.close();
        this.rvDuaTopic.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.rvDuaTopic.setItemAnimator(new DefaultItemAnimator());
        DuaTopicAdapter duaTopicAdapter = new DuaTopicAdapter(this, this.duaTopicModelList, this);
        this.duaTopicAdapter = duaTopicAdapter;
        this.rvDuaTopic.setAdapter(duaTopicAdapter);
    }

    @Override 
    public void OnClick(View view, int i) {
        Intent intent = new Intent(getApplicationContext(), DuaActivity.class);
        intent.putExtra("topicId", this.duaTopicModelList.get(i).getId());
        intent.putExtra("topicName", this.duaTopicModelList.get(i).getTopicName());
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
