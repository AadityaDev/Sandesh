package com.technawabs.sandesh;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.technawabs.sandesh.pojo.PhoneContact;
import com.technawabs.sandesh.pojo.Sms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utility {

    public static String TAG = "Utility";

    public static List<Sms> getAllSmsFromProvider(Context context, Uri uri) {
        List<Sms> lstSms = new ArrayList<Sms>();
        String[] projection = new String[]{"_id", "thread_id", "person", "address", "body", "date", "type"};
        Cursor c = context.getContentResolver().query(uri, projection, null, null,
                "date" + " COLLATE LOCALIZED DESC");
        int totalSMS = c.getCount();
        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                final Sms sms = new Sms();
                sms.set_ID(c.getString(0));
                sms.setPERSON(c.getString(2));
                sms.setADDRESS(c.getString(3));
                sms.setBODY(c.getString(4));
                sms.setDATE(c.getString(5));
                lstSms.add(sms);
                c.moveToNext();
            }
        } else {
            throw new RuntimeException("You have no SMS in Inbox");
        }
        c.close();
        return lstSms;
    }

    public static List<Sms> getAllSmsFromAddress(Context context, Uri uri, String address) {
        List<Sms> lstSms = new ArrayList<Sms>();
        String[] projection = new String[]{"_id", "thread_id", "person", "address", "body", "date", "type"};
        Cursor c = context.getContentResolver().query(uri, projection, null, null,
                "date" + " COLLATE LOCALIZED DESC");
        int totalSMS = c.getCount();
        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                String match = c.getString(3);
                if (match.matches("[0-9]+") && match.length() == 10) {
                    match = "+91" + match;
                }
                if (match.equalsIgnoreCase(address)) {
                    final Sms sms = new Sms();
                    sms.set_ID(c.getString(0));
                    sms.setPERSON(c.getString(2));
                    sms.setADDRESS(c.getString(3));
                    sms.setBODY(c.getString(4));
                    sms.setDATE(c.getString(5));
                    lstSms.add(sms);
                }
                c.moveToNext();
            }
        } else {
            throw new RuntimeException("You have no SMS in Inbox");
        }
        c.close();
        return lstSms;
    }

    public static List<PhoneContact> getContacts(Context context) {
        List<PhoneContact> phoneContactList = new ArrayList<>();
        int count = 0;
        Cursor phones = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                null, null);
        while (phones.moveToNext()) {
            count++;
            String name = phones
                    .getString(phones
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones
                    .getString(phones
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            PhoneContact phoneContact = new PhoneContact();       // creating an instance for contactmodel
            phoneContact.setName(name);
            phoneContact.setNumber(phoneNumber);
            phoneContactList.add(phoneContact);                                               // adding contacts into the list
        }
        phones.close();
        return phoneContactList;
    }

    public static void writeToFile(Context context, String data, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Toast.makeText(context, "Data saved", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "Failed to write in file", Toast.LENGTH_LONG).show();
        }
    }

    public static String readFromFile(Context context, String fileName) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "File not found", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "Can not read file: ", Toast.LENGTH_LONG).show();
        }
        return ret;
    }

}