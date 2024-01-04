package com.example.sidereader;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class OnSwipePdf extends ItemTouchHelper.SimpleCallback{

    MainAdapter mAdapter;

    OnSwipePdf(MainAdapter mAdapter){
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.mAdapter = mAdapter;
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        new AlertDialog.Builder(viewHolder.itemView.getContext())
                .setMessage("Are you sure you want to delete this PDF?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int position = viewHolder.getAdapterPosition();
                        OnSwipePdf.this.mAdapter.undo(position);
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int position = viewHolder.getAdapterPosition();
                        OnSwipePdf.this.mAdapter.deletePdfFile(viewHolder, position);
                    }})
                .create()
                .show();


    }
}