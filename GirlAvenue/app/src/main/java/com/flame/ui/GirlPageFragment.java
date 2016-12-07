package com.flame.ui;



import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.flame.datasource.RemoteLadyFetcher;
import com.flame.ui.adapter.GirlAdapter;
import com.flame.ui.adapter.LadyPagerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 */
public class GirlPageFragment extends BaseFragment {

    LadyPagerAdapter mAdapter;
    int mIndex;
    String mUrl;
    List mSaveList;
    public GirlPageFragment(){
        mSaveList=new ArrayList();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle=getArguments();
        mUrl=bundle.getString("url");
        mIndex=bundle.getInt("index",0);
    }
    public static GirlPageFragment Instance(String url, int index){
        GirlPageFragment fragment=new GirlPageFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        bundle.putInt("index",index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void showProgress() {
    }
    @Override
    public void hideProgress() {
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private String createFileName(){
        RemoteLadyFetcher fetcher=(RemoteLadyFetcher)RemoteLadyFetcher.getInstance();
        String des=fetcher.getLady(mUrl).mDes;
        int index=mIndex+1;
        return des +index +".png";
    }
    private void saveLadyImage(){
        new AsyncTask<Void,Void,Void>(){
            Bitmap bitmap;
            @Override
            protected void onPreExecute() {
                ImageView view=mAdapter.getPrimaryItem();
                bitmap= ((BitmapDrawable) view.getDrawable()).getBitmap();
            }
            @Override
            protected Void doInBackground(Void... params) {
                File path=Environment.getExternalStoragePublicDirectory("GirlAvenue");
                if(!path.exists()){
                    path.mkdir();
                }
                String fileName=createFileName();
                File file=new File(path,fileName);
                try {
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    else {
                        Snackbar.make(getView(),"Image already saved",Snackbar.LENGTH_SHORT).show();
                        return null;
                    }
                    OutputStream outputStream=new FileOutputStream(file);
                    boolean isSaved;
                    if(bitmap==null){
                        isSaved=false;
                    }else {
                        isSaved=bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                    }
                    if(isSaved){
                        Snackbar.make(getView(),fileName+ "have saved in "+path,Snackbar.LENGTH_SHORT).show();
                    }else {
                        Snackbar.make(getView(),"Image saved failed",Snackbar.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lady_view_menu, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // return super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.action_save){
            saveLadyImage();
        }
        return true;
    }

    @Override
    public void fillView(String item) {
        mAdapter.addItem(item);
    }
    @Override
    int getLayout() {
        return R.layout.girl_pager;
    }
    @Override
    void initView(View view) {
        final ViewPager viewPager=(ViewPager) view.findViewById(R.id.view_pager);
        final TextView textView=(TextView)view.findViewById(R.id.index_view);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                int p=position+1;
                textView.setText(p + "/"+mAdapter.getCount());
                mIndex=position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        mAdapter=new LadyPagerAdapter(mUrl);
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int num=mAdapter.getCount();
                textView.setText("1/"+num);

            }
        });
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(mIndex);
        viewPager.getFocusedChild();
    }
}
