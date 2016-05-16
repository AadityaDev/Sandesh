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
import com.technawabs.sandesh.pojo.Sms;

import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder>{

    private List<Sms> smsList;

    public SmsAdapter (List<Sms> smsList){
        this.smsList=smsList;
    }

    @Override
    public SmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_card, parent, false);
        return new SmsViewHolder(parent.getContext(),itemView);
    }

    @Override
    public void onBindViewHolder(SmsViewHolder holder, int position) {
        final Sms sms=smsList.get(position);
        holder.senderName.setText(TextUtils.isEmpty(sms.getPERSON())?sms.getADDRESS():sms.getPERSON());
        holder.senderMessage.setText(sms.getBODY());
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    public String getId(int position) {
        return smsList.get(position).get_ID();
    }

    public String getAddress(int position){
        return smsList.get(position).getADDRESS();
    }

    public static class SmsViewHolder extends RecyclerView.ViewHolder{

        public TextView senderName;
        public TextView senderMessage;
        public ImageView senderImage;

        public SmsViewHolder(Context context,View itemView) {
            super(itemView);
            senderName=(TextView)itemView.findViewById(R.id.sender_name);
            senderMessage=(TextView)itemView.findViewById(R.id.sender_message);
            senderImage=(ImageView)itemView.findViewById(R.id.sender_image);
        }
    }
}
