package com.viztushar.stickers.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.viztushar.stickers.R;
import com.viztushar.stickers.model.StickerModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class StickerDetailsAdapter extends RecyclerView.Adapter<StickerDetailsAdapter.ViewHolder> {

    ArrayList<String> strings;
    Context context;
    String id;

    public StickerDetailsAdapter(ArrayList<String> strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        InputStream iStream = null;
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.sticker_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        Log.d("adapter 2", "onBindViewHolder: " + strings.get(i));
  // String  path= Environment.getExternalStorageDirectory().toString()+ "/WhatsappStickers/9/";
        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/WhatsappStickers/"+"9/");

//traverse(folder);

        if (strings.get(i).endsWith(".jpg")) {
            Glide.with(context)
                    .asBitmap()
                    .load(Uri.parse(strings.get(i)))
                    .apply(new RequestOptions().override(512, 512))
                    .into(viewHolder.imageView);
        } else {
            if (!strings.get(i).endsWith(".png")) {
   Log.d("png :"+iStream, "onBindViewHolder: " + strings.get(i));
                try {
                    iStream = new FileInputStream( strings.get(i));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                StickerModel stickerModel=new StickerModel();
                //String fnamei = ("https://12dtechnology.com/uploads/9/")+strings.get(i).replace(".webp", "").replace(" ", "_") + ".png";
                String fnamei = folder+strings.get(i).replace(".webp", "").replace(" ", "_") + ".png";

                Log.d("Getting_png :"+iStream, "onBindViewHolder: " + strings.get(i));
                Glide.with(context)
                        .asBitmap()
                        .load((fnamei))
                        .into(viewHolder.imageView);
            }
        }
    }


   /*
    public void iterator(File folder){


        if(folder.exists())
            File[] allFiles = folder.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"));
                }
            });


    }
*/



    public void traverse (File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; ++i) {
                File file = files[i];
                if (file.isDirectory()) {
                    traverse(file);
                } else {
                    // do something here with the file
                }
            }
        }
    }
    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sticker_image);
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }
}
