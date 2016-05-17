package com.technawabs.sandesh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technawabs.sandesh.R;
import com.technawabs.sandesh.pojo.Sms;

import java.util.List;

public class SmsDetailAdapter extends RecyclerView.Adapter<SmsDetailAdapter.SmsDetailViewHolder> {

    private List<Sms> smsList;

    public SmsDetailAdapter(List<Sms> smsList) {
        this.smsList = smsList;
    }

    @Override
    public SmsDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_detail_card, parent, false);
        return new SmsDetailViewHolder(parent.getContext(), itemView);
    }

    @Override
    public void onBindViewHolder(SmsDetailViewHolder holder, int position) {
        final Sms sms = smsList.get(position);
        holder.message.setText(sms.getBODY());
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    public String getId(int position) {
        return smsList.get(position).get_ID();
    }

    public static class SmsDetailViewHolder extends RecyclerView.ViewHolder {
        public Context context;
        public TextView message;

        public SmsDetailViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            message = (TextView) itemView.findViewById(R.id.sender_message);
        }
    }

}
