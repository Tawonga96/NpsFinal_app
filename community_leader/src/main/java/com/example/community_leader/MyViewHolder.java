package com.example.community_leader;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    android.widget.ImageView ImageView;
    TextView nameView,  emailView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        ImageView = itemView.findViewById(R.id.Item_ImageView);
        nameView = itemView.findViewById(R.id.name_id);
        emailView= itemView.findViewById(R.id.email_id);
    }
}
