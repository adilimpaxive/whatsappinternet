package com.viztushar.stickers.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.viztushar.stickers.BuildConfig;
import com.viztushar.stickers.MainActivity;
import com.viztushar.stickers.R;
import com.viztushar.stickers.Sticker;
import com.viztushar.stickers.StickerPack;
import com.viztushar.stickers.adapter.StickerDetailsAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.viztushar.stickers.MainActivity.EXTRA_STICKER_PACK_AUTHORITY;
import static com.viztushar.stickers.MainActivity.EXTRA_STICKER_PACK_ID;
import static com.viztushar.stickers.MainActivity.EXTRA_STICKER_PACK_NAME;

public class StickerDetailsActivity extends AppCompatActivity {

    private static final int ADD_PACK = 200;
    private static final String TAG = StickerDetailsActivity.class.getSimpleName();
    StickerPack stickerPack;
    StickerDetailsAdapter adapter;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Sticker> stickers;
    ArrayList<String> strings;
    public static String path;
    Button addtowhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_details);
        stickerPack = getIntent().getParcelableExtra(MainActivity.EXTRA_STICKERPACK);
        toolbar = findViewById(R.id.toolbar);
        addtowhatsapp = findViewById(R.id.add_to_whatsapp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(stickerPack.name);
        getSupportActionBar().setSubtitle(stickerPack.publisher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        stickers = stickerPack.getStickers();
        strings = new ArrayList<>();
       // path= Environment.getExternalStorageDirectory().toString()+ "/" + " ";
    //path = getFilesDir() + "/" + "stickers_asset" + "/" + stickerPack.identifier + "/";
      //  path = Environment.getExternalStorageDirectory().toString() + "/" + "";

       // path= Environment.getExternalStorageDirectory().toString()+ "/WhatsappStickers/"+ stickerPack.identifier+ "/";
        path= Environment.getExternalStorageDirectory().toString()+ "/WhatsappStickers/" + "9/";
        Log.e("path is ",path);
      //  path = Environment.getExternalStorageDirectory().toString() + "/" + "stickers_asset" + "/" + stickerPack.identifier+ "/" ;
       // path =getFilesDir()+  "/" + stickerPack.identifier + "/"+ "try"+ "/";
        File file = new File(path + stickers.get(0).imageFileName);
        Log.d(TAG, "onCreate: " +path + stickers.get(0).imageFileName);
        for (Sticker s : stickers) {
            if ((file ==null))
            {

                strings.add(s.imageFileName);
                //Log.wtf("mg",s.imageFileName);
            }
                }
        adapter = new StickerDetailsAdapter(strings, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        addtowhatsapp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
                intent.putExtra(EXTRA_STICKER_PACK_ID, stickerPack.identifier);
                intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, BuildConfig.CONTENT_PROVIDER_AUTHORITY);
                intent.putExtra(EXTRA_STICKER_PACK_NAME, stickerPack.name);
                try {
                    startActivityForResult(intent, ADD_PACK);

                }    catch (ActivityNotFoundException e) {
                    Log.e("sdg",e.toString());
                    Toast.makeText(StickerDetailsActivity.this, "error"+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @NonNull
    private Intent createIntentToAddStickerPack(String identifier, String stickerPackName) {
        Intent intent = new Intent();
        intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
        intent.putExtra(EXTRA_STICKER_PACK_ID, identifier);
        intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, BuildConfig.CONTENT_PROVIDER_AUTHORITY);
        intent.putExtra(EXTRA_STICKER_PACK_NAME, stickerPackName);
        return intent;
    }


}
