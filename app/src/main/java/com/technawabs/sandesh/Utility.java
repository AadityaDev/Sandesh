package com.technawabs.sandesh;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    public static String TAG = "Utility";

    public static void getSMS(Context context, Cursor cursor, Uri uri) {
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
        System.out.println(smsBuilder.toString());
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
