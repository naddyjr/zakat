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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.PreferenceUtil.NotificationPref;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NotificationActivity extends AppCompatActivity {
    LinearLayout btnSetTime;
    String format = "";
    RelativeLayout layout;
    NotificationPref notificationPref;
    TimePicker timePiker;
    TextView tvTime;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_notification);




        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.tvTime = (TextView) findViewById(R.id.tvTime);
        this.timePiker = (TimePicker) findViewById(R.id.timePiker);
        this.btnSetTime = (LinearLayout) findViewById(R.id.btnSetTime);
        NotificationPref notificationPref = new NotificationPref(this);
        this.notificationPref = notificationPref;
        setTime(notificationPref.getTime());
        this.btnSetTime.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                int intValue = NotificationActivity.this.timePiker.getCurrentHour().intValue();
                int intValue2 = NotificationActivity.this.timePiker.getCurrentMinute().intValue();
                NotificationActivity.this.showTime(intValue, intValue2);
                NotificationActivity.this.notificationPref.setTime(NotificationActivity.this.hmToLong(intValue, intValue2));
                Toast.makeText(NotificationActivity.this, "Set time successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setTime(long j) {
        Date date = new Date(j);
        showTime(date.getHours(), date.getMinutes());
        this.timePiker.setCurrentHour(Integer.valueOf(date.getHours()));
        this.timePiker.setCurrentMinute(Integer.valueOf(date.getMinutes()));
    }

    public long hmToLong(int i, int i2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm");
        try {
            return simpleDateFormat.parse(i + ":" + i2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public void showTime(int i, int i2) {
        if (i == 0) {
            i += 12;
            this.format = "AM";
        } else if (i == 12) {
            this.format = "PM";
        } else if (i > 12) {
            i -= 12;
            this.format = "PM";
        } else {
            this.format = "AM";
        }
        String valueOf = String.valueOf(i);
        String valueOf2 = String.valueOf(i2);
        if (i < 10) {
            valueOf = "0" + i;
        }
        if (i2 < 10) {
            valueOf2 = "0" + i2;
        }
        TextView textView = this.tvTime;
        StringBuilder sb = new StringBuilder();
        sb.append(valueOf);
        sb.append(" : ");
        sb.append(valueOf2);
        sb.append(" ");
        sb.append(this.format);
        textView.setText(sb);
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
