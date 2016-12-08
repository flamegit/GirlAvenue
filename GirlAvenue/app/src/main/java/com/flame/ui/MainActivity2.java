//package com.flame.ui;
//
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import com.flame.ui.adapter.LadyFragmentAdapter;
//import com.flame.utils.NetWorkUtils;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_xml);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if(!NetWorkUtils.isNetworkConnected(this)){
//
//        }
//        TabLayout tabLayout=(TabLayout)findViewById(R.id.tab_layout);
//        ViewPager viewPager=(ViewPager)findViewById(R.id.fragment_view);
//        PagerAdapter adapter=new LadyFragmentAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
//    }
//    private void showEmptyView(){
//    }
//
//    private void showDetail(){
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//}
