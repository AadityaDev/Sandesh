package com.technawabs.sandesh;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;

import com.technawabs.sandesh.pojo.Sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utility {

    public static String TAG = "Utility";

    public static List<Sms> getSMS(Context context, Cursor cursor, Uri uri) {
        List<Sms> smsList = new ArrayList<Sms>();
        StringBuilder smsBuilder = new StringBuilder();
        try {
            while (cursor.moveToNext()) {
                String[] projection = new String[]{"_id", "address", "thread_id", "body", "date", "type"};
                Cursor cur = context.getContentResolver().query(uri, projection, "thread_id=?", new String[]{cursor.getString(0)},
                        "date" + " COLLATE LOCALIZED DESC");
                if (cur.moveToNext()) {
                    String address = cur.getString(cur.getColumnIndex("address"));
                    String name = null;
                    String photoUri = null;
                    if (address != null) {
                        if (address.length() > 0) {
                            // getting contact by contact number
                            String[] contactData = Utility.getContactByNumber(context, address);
                            if (contactData != null) {
                                name = contactData[0];
                                if (contactData[1] != null)
                                    photoUri = contactData[1];
                            }
                        } else
                            address = null;
                    }
                    String body = cur.getString(cur.getColumnIndexOrThrow("body"));
                    int int_Type = cur.getInt(cur.getColumnIndexOrThrow("type"));
                    long longDate = cur.getLong(cur.getColumnIndexOrThrow("date"));
                    String dateString = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date(longDate));
                    final Sms sms = new Sms();
                    sms.setADDRESS(address);
                    sms.setPERSON(TextUtils.isDigitsOnly(name) ? name : address);
                    sms.setIMAGE_URI(TextUtils.isEmpty(photoUri) ? photoUri : "");
                    sms.setBODY(body);
                    sms.setDATE(dateString);
                    smsList.add(sms);
                    smsBuilder.append("[ ");
                    smsBuilder.append(address + ", ");
                    smsBuilder.append(name + ", ");
                    smsBuilder.append(body + ", ");
                    smsBuilder.append(longDate + ", ");
                    smsBuilder.append(int_Type + ", ");
                    smsBuilder.append(photoUri);
                    smsBuilder.append(" ]\n\n");
                }
                cur.close();
            }
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        } catch (OutOfMemoryError e) {
            Log.i(TAG, e.getMessage());
        }
        cursor.close();
        return smsList;
    }

    /**
     * Get Contact Name by Contact Number
     *
     * @param number
     * @return
     */
    private static String[] getContactByNumber(Context context, final String number) {
        String[] data = new String[2];
        try {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                    Uri.encode(number));
            Cursor cur = context.getContentResolver().query(uri,
                    new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID},
                    null, null, null);
            if (cur.moveToFirst()) {
                int nameIdx = cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                data[0] = cur.getString(nameIdx);
                String contactId = cur.getString(cur
                        .getColumnIndex(ContactsContract.PhoneLookup._ID));
// getting contact image uri of the contac by contact id
                String photoUri = getContactPhotoUri(Long.parseLong(contactId));
                if (photoUri != null)
                    data[1] = photoUri;
                else
                    data[1] = null;
                cur.close();
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Sms> getAllSmsFromProvider(Context context, Uri uri) {
        List<Sms> lstSms = new ArrayList<Sms>();
        ContentResolver cr = context.getContentResolver();

        String[] projection = new String[]{"_id", "thread_id","person", "address", "body", "date", "type"};
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

    public static List<Sms> getAllSmsFromAddress(Context context, Uri uri,String address) {
        List<Sms> lstSms = new ArrayList<Sms>();
        String[] phoneNumber = new String[] { "*" };
        String[] projection = new String[]{"_id", "thread_id","person", "address", "body", "date", "type"};
        Cursor c = context.getContentResolver().query(uri, phoneNumber, null,null,
                "date" + " COLLATE LOCALIZED DESC");
//        Cursor c = context.getContentResolver().query(uri, projection, "address=?",phoneNumber,
//                "date" + " COLLATE LOCALIZED DESC");
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

    /**
     * Get contact photo URI from contact Id
     *
     * @param contactId
     * @return imagePath
     */
    private static String getContactPhotoUri(long contactId) {
        Uri photoUri = ContentUris.withAppendedId(Contacts.CONTENT_URI,
                contactId);
        String imagePath = null;
        if (photoUri != null) {
            imagePath = photoUri.toString();
        }
        return imagePath;
    }
}
