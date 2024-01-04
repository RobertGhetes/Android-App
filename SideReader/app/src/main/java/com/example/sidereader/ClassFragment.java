package com.example.sidereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;


public class ClassFragment extends Fragment implements OnClassSelectListener{

    View view;
    List<Courses> coursesList;
    MyDatabaseHelper myDB;
    FloatingActionButton addButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_class, container, false);
        addButton = view.findViewById(R.id.add_Button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDialog();
            }
        });
        myDB = new MyDatabaseHelper(getActivity().getApplicationContext());
        displayCourses();
        return view;
    }

    private void openAddDialog() {
        AddDialog addDialog = new AddDialog();
        addDialog.show(getParentFragmentManager()  , "add dialog");
    }

    public void displayCourses()
    {
        coursesList = myDB.readAllData();
        RecyclerView recyclerView = view.findViewById(R.id.coursesRv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        CoursesAdapter coursesAdapter = new CoursesAdapter(coursesList, getActivity(), this::onClassSelected);
        recyclerView.setAdapter(coursesAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeItem(coursesAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClassSelected(@NonNull Courses course) {
        String path;
        path = course.getCourseURL();
        Intent intent = new Intent(getActivity(), DriveActivity.class);
        intent.putExtra(intent.EXTRA_TEXT,path);
        startActivity(intent);
    }



}