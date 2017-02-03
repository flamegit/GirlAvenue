package com.flame.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import com.flame.Constants;
public class LadyViewActivity extends AppCompatActivity {
    public static String sUrl;
    public static String sDes;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lady_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fillFragment();
    }

    private void fillFragment(){
        Intent intent = getIntent();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        String url = intent.getStringExtra(Constants.URL);
        String desc = intent.getStringExtra(Constants.DEC);
        int index=intent.getIntExtra(Constants.INDEX,0);
        if(fragment==null){
            if (Constants.SHOW_DETAIL_ACTION.equals(intent.getAction())) {
                mToolbar.setBackgroundColor(Color.BLACK);
                fragment = GirlPageFragment.Instance(url,index);
            }else if(Constants.SHOW_FAVORITE_ACTION.equals(intent.getAction())){
                fragment=GirlListFragment.Instance(null,Constants.FAVORITE);
            }else if(Constants.SHOW_TAG_ACTION.equals(intent.getAction())){
                fragment=GirlListFragment.Instance(url,Constants.TAG);
            }
            else {
                fragment = LadyPreViewFragment.Instance(url, desc);
            }
            getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
        }
    }

    public void changeTitle(String title){
        mToolbar.setTitle(title);
    }


    public void showToolbar(boolean show) {
        if (show) {
            mToolbar.animate().alpha(1);
        } else {
            mToolbar.animate().alpha(0);
        }
    }

    public static void jumpToActivity(Context context,String action,String url, String dec, int index){
        Intent intent=new Intent(context,LadyViewActivity.class);
        intent.setAction(action);
        intent.putExtra(Constants.URL,url);
        intent.putExtra(Constants.DEC,dec);
        intent.putExtra(Constants.INDEX,index);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
