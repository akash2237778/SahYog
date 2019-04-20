package com.example.sahyog;

import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterMsg extends RecyclerView.Adapter<AdapterMsg.MsgProgramViewHolder> {
    private  String[] senderNameData;
    private String[] msgDateData;



    public AdapterMsg(String[] Senderdata){
        this.senderNameData = Senderdata;
        //this.msgDateData = msgdate;

    }


    @NonNull
    @Override
    public AdapterMsg.MsgProgramViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recycler_view_item,viewGroup,false);

        return new AdapterMsg.MsgProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMsg.MsgProgramViewHolder mprogramViewHolder, int i) {
        String senderName = senderNameData[i];
        //String msgDate = msgDateData[i];



        mprogramViewHolder.textView.setText(senderName);
        //mprogramViewHolder.textDataView.setText(msgDate);



    }

    @Override
    public int getItemCount() {
        return  senderNameData.length;
    }

    public class MsgProgramViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        //TextView textDataView;
        //TextView textAddressView;
        //TextView textStatusView;

        public MsgProgramViewHolder(@NonNull View itemView) {
            super(itemView);

        //    imgview = (ImageView) itemView.findViewById(R.id.statusImageView);
            textView = (TextView) itemView.findViewById(R.id.textViewSender);
            //textDataView = (TextView) itemView.findViewById(R.id.textViewDate);
          //  textAddressView = (TextView) itemView.findViewById(R.id.AddressTextView);
            //textStatusView = (TextView) itemView.findViewById(R.id.statusView);

        }
    }
}
