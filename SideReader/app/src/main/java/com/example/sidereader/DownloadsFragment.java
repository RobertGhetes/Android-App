package com.example.sidereader;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DownloadsFragment extends Fragment implements OnPdfDownloadSelectListener {

    View view;
    private MainAdapter adapter;
    private List<File> pdfList;
    private RecyclerView recyclerView;
    private File downloadsFile = new File("/storage/emulated/0/Android/data/com.example.sidereader/files");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_downloads, container, false);
        displayPdf();
        return view;
    }

    public ArrayList<File> findPdf(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        if(files!= null)
        {
        for (File singleFile : files) {
            if (singleFile.isDirectory()  && !singleFile.isHidden()) {
                arrayList.addAll(findPdf(singleFile));

            } else {
                if (singleFile.getName().endsWith(".pdf")&& singleFile.getParentFile().getPath().equals("/storage/emulated/0/Android/data/com.example.sidereader/files")) {
                    arrayList.add(singleFile);
                }
            }

        }}
        else{
        }
        return arrayList;
    }

    public void displayPdf()
    {
         recyclerView = view.findViewById(R.id.rvDownloads);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        pdfList = new ArrayList<>();
        pdfList.addAll(findPdf(downloadsFile));
        adapter = new MainAdapter(getActivity(), pdfList, this::onPdfDownloadSelected);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new OnSwipePdf(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onPdfDownloadSelected(File file) {
        startActivity(new Intent(getActivity(),PdfActivity.class).putExtra("path",file.getAbsolutePath()));
    }

}