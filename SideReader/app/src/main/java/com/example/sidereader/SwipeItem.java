package com.example.sidereader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeItem extends ItemTouchHelper.SimpleCallback {

    private CoursesAdapter mCoursesAdapter;
    private MainAdapter mAdapter;
    private AlertDialog dialog;

    SwipeItem(CoursesAdapter coursesAdapter){
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.mCoursesAdapter = coursesAdapter;
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        new AlertDialog.Builder(viewHolder.itemView.getContext())
                .setMessage("Are you sure you want to delete this class?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int position = viewHolder.getAdapterPosition();
                        SwipeItem.this.mCoursesAdapter.showAll(position);
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int position = viewHolder.getAdapterPosition();
                        SwipeItem.this.mCoursesAdapter.deleteCourse(position);
                    }})
                .create()
                .show();

    }
}
