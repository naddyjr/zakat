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
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.load.Key;

import com.somnest.zakaat.Adapter.ViewPager.HajjJourneyAdapter;
import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.HijriCalender.DatabaseHelper;
import com.somnest.zakaat.Model.HajjModel;
import com.somnest.zakaat.OnItemClickListener.OnItemClickListener;
import com.somnest.zakaat.Util.Params;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;


public class HajjJourneyActivity extends AppCompatActivity implements OnItemClickListener {
    LinearLayout btnBack;
    LinearLayout btnNext;
    List<HajjModel> hajjModelList = new ArrayList();
    RelativeLayout layout;
    ViewPager vpGuide;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_hajj_journey);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.btnNext = (LinearLayout) findViewById(R.id.btnNext);
        this.btnBack = (LinearLayout) findViewById(R.id.btnBack);
        this.vpGuide = (ViewPager) findViewById(R.id.vpGuide);
        this.hajjModelList = getHajjDetail();
        this.vpGuide.setAdapter(new HajjJourneyAdapter(this, this.hajjModelList, this));
        this.btnBack.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (HajjJourneyActivity.this.vpGuide.getCurrentItem() != 0) {
                    HajjJourneyActivity.this.vpGuide.setCurrentItem(HajjJourneyActivity.this.vpGuide.getCurrentItem() - 1);
                    return;
                }
                Toast.makeText(HajjJourneyActivity.this, "Your journey is start", Toast.LENGTH_SHORT).show();
            }
        });
        this.btnNext.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (HajjJourneyActivity.this.vpGuide.getCurrentItem() != HajjJourneyActivity.this.hajjModelList.size() - 1) {
                    HajjJourneyActivity.this.vpGuide.setCurrentItem(HajjJourneyActivity.this.vpGuide.getCurrentItem() + 1);
                    return;
                }
                Toast.makeText(HajjJourneyActivity.this, "Your journey is end", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<HajjModel> getHajjDetail() {
        String str;
        ArrayList arrayList = new ArrayList();
        try {
            InputStream open = getAssets().open("hajj.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            str = new String(bArr, Key.STRING_CHARSET_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                HajjModel hajjModel = new HajjModel();
                hajjModel.setTitle(jSONObject.getString("title"));
                hajjModel.setDay(jSONObject.getString(DatabaseHelper.COL_2));
                hajjModel.setDate(jSONObject.getString("date"));
                hajjModel.setSubTitle(jSONObject.getString("sub_title"));
                hajjModel.setDescription(jSONObject.getString(Params.ATHKAR_DESCRIPTION_COL));
                hajjModel.setSubDescription(jSONObject.getString("sub_desc"));
                hajjModel.setHistory(jSONObject.getString("history"));
                hajjModel.setArchitecture(jSONObject.getString("architecture"));
                hajjModel.setImgName(jSONObject.getString("banner_img"));
                hajjModel.setLatitude(jSONObject.getString("latitude"));
                hajjModel.setLongitude(jSONObject.getString("longitude"));
                arrayList.add(hajjModel);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    @Override 
    public void OnClick(View view, int i) {
        Intent intent = new Intent(this, HajjDetailActivity.class);
        intent.putExtra("hajjModel", this.hajjModelList.get(i));
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
