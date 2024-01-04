package com.example.sidereader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Context context;
    private List<File> pdfFiles;
    private OnPdfSelectListener listener;
    private String fullPath;
    private String fileName;

    public MainAdapter(Context context, List<File> pdfFiles, OnPdfSelectListener listener) {
        this.context = context;
        this.pdfFiles = pdfFiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.txtName.setText(pdfFiles.get(holder.getAdapterPosition()).getName());
        holder.txtName.setSelected(true);
        fileName = pdfFiles.get(holder.getAdapterPosition()).getName();
        holder.txtPath.setText(pdfFiles.get(holder.getAdapterPosition()).getPath());
        fullPath = pdfFiles.get(holder.getAdapterPosition()).getPath();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPdfSelected(pdfFiles.get(holder.getAdapterPosition()));
            }
        });

    }

    public void undo(int position)
    {
        notifyItemChanged(position);
    }

    public void deletePdfFile(RecyclerView.ViewHolder holder, int position){
        fullPath = this.pdfFiles.get(holder.getAdapterPosition()).getPath();
        this.pdfFiles.remove(position);
        File file = new File(fullPath);

        if (file.exists()) {
                if(file.delete())
                {
                    Toast.makeText(context.getApplicationContext(), "File deleted ", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context.getApplicationContext(), "Unable to delete file ", Toast.LENGTH_LONG).show();}

        }
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }
}
