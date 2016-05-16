package com.technawabs.sandesh.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.technawabs.sandesh.R;
import com.technawabs.sandesh.SandeshConstants;
import com.technawabs.sandesh.Utility;
import com.technawabs.sandesh.adapter.SmsAdapter;
import com.technawabs.sandesh.pojo.Sms;

import java.util.ArrayList;
import java.util.List;

public class SmsDetail extends AppCompatActivity {

    private List<Sms> smsList;
    private RecyclerView recList;
    private LinearLayoutManager linearLayoutManager;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_detail);
        bundle = getIntent().getExtras();
        recList = (RecyclerView) findViewById(R.id.conversation_list);
        recList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(linearLayoutManager);
        smsList = new ArrayList<>();
        final SmsAdapter smsAdapter = new SmsAdapter(smsList);
        recList.setAdapter(smsAdapter);
        try {
            smsList.addAll(Utility.getAllSmsFromAddress(getApplicationContext(),
                    Uri.parse(SandeshConstants.SMS_CONVERSATION), bundle.getString("address")));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
        }
        smsAdapter.notifyDataSetChanged();
    }
}
