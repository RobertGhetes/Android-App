package com.example.sidereader;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity{

    Button driveFragmentBtn, memoryFragmentBtn, downloadsFragmentBtn;
    TextView tv1;
    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private boolean isReadPermissionGranted = false;
    private boolean isWritePermissionGranted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> result) {
                        if (result.get(Manifest.permission.READ_EXTERNAL_STORAGE) != null) {
                            isReadPermissionGranted = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                        if (result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) != null) {
                            isWritePermissionGranted = result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                    }
                });
        runtimePermission();
        driveFragmentBtn = findViewById(R.id.driveFragmentBtn);
        memoryFragmentBtn = findViewById(R.id.memoryFragmentBtn);
        downloadsFragmentBtn = findViewById(R.id.downloadsFragmentBtn);
        tv1 = (TextView)findViewById(R.id.textView);
        driveFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setText("Drive");
                replaceFragment(new  ClassFragment());

            }
        });

        memoryFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setText("Memory");
                replaceFragment(new MemoryFragment());

            }
        });

        downloadsFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setText("Downloads");
                replaceFragment((new DownloadsFragment()));
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new DownloadsFragment()).commit();
        startup();

    }




    private void runtimePermission() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
        isWritePermissionGranted = ContextCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
        List<String> permissionRequest = new ArrayList<String>();

        if(!isReadPermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(!isWritePermissionGranted){
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if(!permissionRequest.isEmpty())
        {
            mPermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
        }
    }

    private void startup(){
        TextView tv1 = (TextView)findViewById(R.id.textView);
        tv1.setText("Downloads");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new DownloadsFragment()).commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}