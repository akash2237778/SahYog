package com.example.sahyog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterProgram extends RecyclerView.Adapter<AdapterProgram.ProgramViewHolder>{

    private  String[] data;
    public AdapterProgram(String[] data){
        this.data = data;
    }


    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recycler_view_item,viewGroup,false);

        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder programViewHolder, int i) {
        String name = data[i];
        programViewHolder.textView.setText(name);
        programViewHolder.textDataView.setText("Hello this is first job available through shayog. Urgently needed if anyone available please contact!!");

    }

    @Override
    public int getItemCount() {
        return  data.length;
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {
        ImageView imgview;
        TextView textView;
        TextView textDataView;

        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);

            imgview = (ImageView) itemView.findViewById(R.id.imageView2);
            textView = (TextView) itemView.findViewById(R.id.textView);
            textDataView = (TextView) itemView.findViewById(R.id.textView2);
        }
    }
}
