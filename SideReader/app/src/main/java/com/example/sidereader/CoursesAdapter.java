package com.example.sidereader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {
    List<Courses> coursesList;
    Context context;
    OnClassSelectListener listener;
    MyDatabaseHelper myDB;
    public int refresh = -1;



    public CoursesAdapter(List<Courses> coursesList, Context context, OnClassSelectListener listener) {
        this.coursesList = coursesList;
        this.context = context;
        this.listener = listener;
    }



    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CoursesViewHolder(LayoutInflater.from(context).inflate(R.layout.courses_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        holder.class_Name.setText(coursesList.get(position).getName());
        holder.class_Url.setText(coursesList.get(position).getCourseURL());
        holder.class_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClassSelected(coursesList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder {
        TextView class_Name;
        TextView class_Url;
        CardView class_View;

        public CoursesViewHolder(@NonNull View itemView) {
            super(itemView);

            class_Name = itemView.findViewById(R.id.class_Name);
            class_Url = itemView.findViewById(R.id.class_Url);
            class_View = itemView.findViewById(R.id.coursesCardView);
        }
    }
    public void showAll(int position){
        notifyItemChanged(position);
    }

    public void deleteCourse(int position){
        String remove;
        this.coursesList.remove(position);
        myDB = new MyDatabaseHelper(context.getApplicationContext());
        myDB.readAllData();
        myDB.getCurrentId(position);
        remove = String.valueOf( myDB.getCurrentId(position));
        myDB.deleteOneRow(remove);
        notifyItemRemoved(position);
    }

}
