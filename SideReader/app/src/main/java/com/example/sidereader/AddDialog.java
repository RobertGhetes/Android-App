package com.example.sidereader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class AddDialog extends AppCompatDialogFragment  {
    private EditText editClassName;
    private EditText editClassUrl;
    private Button confirmButton;
    List<Courses> coursesList;
    private String className;
    private String classUrl;
    private MyDatabaseHelper myDB;
    private AlertDialog dialog;

    @NonNull
    @Override
    public  Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog, null);
        myDB = new MyDatabaseHelper(getActivity().getApplicationContext());
        coursesList = myDB.readAllData();
        dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Add class")
                .setPositiveButton("Confirm", null)
                .setNegativeButton("cancel", null)
                .show();

        editClassName = view.findViewById(R.id.edit_className);
        editClassUrl = view.findViewById(R.id.edit_Url);
        confirmButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        confirmButton.setEnabled(false);
        editClassName.addTextChangedListener(addDialogWatcher);
        editClassUrl.addTextChangedListener(addDialogWatcher);

        return dialog;
    }

    public TextWatcher addDialogWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            confirmButton.setEnabled(false);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            className = editClassName.getText().toString().trim();
            classUrl = editClassUrl.getText().toString().trim();
            confirmButton.setEnabled(!className.isEmpty() && !classUrl.isEmpty());
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isUrlValid(classUrl) && classUrl.startsWith("https://drive.google.com/drive/folders/")) {
                        int nextId = (int) myDB.getProfilesCount();
                        nextId++;
                        if (myDB.idInUse(nextId)) {
                            myDB.updateId();
                        }
                        if(!myDB.urlInUse(classUrl)) {
                            myDB.addCourse(nextId,
                                    className.trim(),
                                    classUrl.trim());
                            Courses newCourse = new Courses(nextId, className, classUrl);
                            coursesList.add(newCourse);
                            dialog.dismiss();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new ClassFragment()).commit();

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Google Drive URL already in use", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please enter a valid Google Drive URL", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public static boolean isUrlValid(String url) {
        try {
            URL obj = new URL(url);
            obj.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }


}
