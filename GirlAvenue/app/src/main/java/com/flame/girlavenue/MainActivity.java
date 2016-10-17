package com.flame.girlavenue;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.flame.Adapter.GirlAdapter;
import com.flame.datasource.RemoteGirlFetcher;
import com.flame.model.Response;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {


    OkHttpClient mClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager=(ViewPager) findViewById(R.id.view_pager);
        final GirlAdapter adapter=new GirlAdapter();
        viewPager.setAdapter(adapter);

        //Picasso picasso=Picasso.with(this);

//        RemoteGirlFetcher.getInstance().getGirlList(new RemoteGirlFetcher.CallBack() {
//            @Override
//            public void onLoad(List<Response.Girl> results) {
//                adapter.addItems(results);
//            }
//        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    static File createDefaultCacheDir(Context context) {
        File cache = new File(context.getCacheDir(), "OkHttpCache");
        if (!cache.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cache.mkdirs();
        }
        return cache;
    }

    private void createClient(){


       // clientcache(new Cache(createDefaultCacheDir(this),50*1024*1024));
    }

    private void makeRequest(){
        Request request =new Request.Builder()
                .url("http://ww4.sinaimg.cn/large/610dc034jw1f8bc5c5n4nj20u00irgn8.jpg")
                .addHeader("Cache-Control", "public, max-age=5" )
                // .cacheControl(new CacheControl.Builder().)
               // .cacheControl(new CacheControl.Builder().noCache().build())
                .build();
        if(mClient==null){
            mClient=new OkHttpClient.Builder().cache(new Cache(createDefaultCacheDir(this),10*1024*1024)).build();
        }
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Okhttp",e.toString());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                  Log.d("Okhttp","onresponse");
               if(response.cacheResponse()==null){
                   Log.d("Okhttp","on read cache");
               }
                response.body().close();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
