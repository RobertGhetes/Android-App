package com.example.sidereader;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder {
    public TextView txtName;
    public CardView cardView;
    public TextView txtPath;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.pdf_Name);
        cardView = itemView.findViewById(R.id.cardView);
        txtPath = itemView.findViewById(R.id.pdf_path);
    }
}
