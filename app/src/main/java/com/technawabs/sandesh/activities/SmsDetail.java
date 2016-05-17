package com.technawabs.sandesh.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.technawabs.sandesh.R;
import com.technawabs.sandesh.SandeshConstants;
import com.technawabs.sandesh.Utility;
import com.technawabs.sandesh.adapter.SmsAdapter;
import com.technawabs.sandesh.adapter.SmsDetailAdapter;
import com.technawabs.sandesh.pojo.Sms;

import java.util.ArrayList;
import java.util.List;

public class SmsDetail extends AppCompatActivity {

    private final String TAG = getClass().getName();
    private List<Sms> smsList;
    private RecyclerView recList;
    private LinearLayoutManager linearLayoutManager;
    private ImageView sendMessage;
    private EditText composedMessage;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_detail);
        bundle = getIntent().getExtras();
        setTitle(bundle.getString("address"));
        sendMessage = (ImageView) findViewById(R.id.send_message);
        recList = (RecyclerView) findViewById(R.id.conversation_list);
        recList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(linearLayoutManager);
        smsList = new ArrayList<Sms>();
        final SmsDetailAdapter smsAdapter = new SmsDetailAdapter(smsList);
        recList.setAdapter(smsAdapter);
        Log.i(TAG, bundle.getString("address"));
        try {
            smsList.addAll(Utility.getAllSmsFromAddress(getApplicationContext(),
                    Uri.parse(SandeshConstants.SMS), bundle.getString("address")));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
        }
        smsAdapter.notifyDataSetChanged();
        composedMessage = (EditText) findViewById(R.id.compose_message);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(composedMessage.getText().toString())) {
                    sendSMS(bundle.getString("address"), composedMessage.getText().toString());
                    composedMessage.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
}
