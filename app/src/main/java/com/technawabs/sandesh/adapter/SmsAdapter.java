package com.technawabs.sandesh.adapter;

import android.content.Context;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technawabs.sandesh.R;
import com.technawabs.sandesh.pojo.PhoneContact;
import com.technawabs.sandesh.pojo.Sms;

import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder> {

    private List<Sms> smsList;
    private List<PhoneContact> phoneContactList;
    private boolean isContact;

    public SmsAdapter(List<PhoneContact> phoneContactList, boolean isContact) {
        this.phoneContactList = phoneContactList;
        this.isContact = isContact;
    }

    public SmsAdapter(List<Sms> smsList) {
        this.smsList = smsList;
    }

    @Override
    public SmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_card, parent, false);
        return new SmsViewHolder(parent.getContext(), itemView);
    }

    @Override
    public void onBindViewHolder(SmsViewHolder holder, int position) {
        if (isContact) {
            final PhoneContact phoneContact = phoneContactList.get(position);
            holder.senderName.setText(phoneContact.getName());
            holder.senderMessage.setText(phoneContact.getNumber());
        } else {
            final Sms sms = smsList.get(position);
            holder.senderName.setText(TextUtils.isEmpty(sms.getPERSON()) ? sms.getADDRESS() : sms.getPERSON());
            holder.senderMessage.setText(sms.getBODY());
        }
    }

    @Override
    public int getItemCount() {
        if(isContact){
            return phoneContactList.size();
        }else {
            return smsList.size();
        }

    }

    public String getId(int position) {
        if(isContact){
            return phoneContactList.get(position).getId();
        }else {
            return smsList.get(position).get_ID();
        }
    }

    public String getAddress(int position) {
        return smsList.get(position).getADDRESS();
    }

    public static class SmsViewHolder extends RecyclerView.ViewHolder {

        public TextView senderName;
        public TextView senderMessage;
        public ImageView senderImage;

        public SmsViewHolder(Context context, View itemView) {
            super(itemView);
            senderName = (TextView) itemView.findViewById(R.id.sender_name);
            senderMessage = (TextView) itemView.findViewById(R.id.sender_message);
            senderImage = (ImageView) itemView.findViewById(R.id.sender_image);
        }
    }
}
