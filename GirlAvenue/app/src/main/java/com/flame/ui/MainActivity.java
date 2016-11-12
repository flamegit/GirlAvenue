package com.flame.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.flame.datasource.Fetcher;
import com.flame.datasource.RemoteLadylFetcher;
import com.flame.model.Lady;
import com.flame.presenter.GirlPresenter;
import com.flame.utils.NetWorkUtils;
import com.flame.utils.RxBus;

import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    Fetcher mfetcher;
    GirlListFragment mfragment;
    Subscription mSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!NetWorkUtils.isNetworkConnected(this)){

        }
        if (savedInstanceState == null) {
            mfragment=new GirlListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content,mfragment)
                    .commit();
        }
        mfetcher=RemoteLadylFetcher.getInstance();
        new GirlPresenter(mfragment,mfetcher);

        mSubscription= RxBus.getDefault().toObservable(Lady.class).subscribe(new Action1<Lady>() {
            @Override
            public void call(Lady lady) {
                startPageFragment(lady.mUrl,lady.mDes);
            }
        });

        
    }
    private void showEmptyView(){

    }
    private void startPageFragment(String url,String desc){
        LadyPreViewFragment fragment=LadyPreViewFragment.Instance(url,desc);
        new GirlPresenter(fragment,mfetcher);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss ();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();

    }
}
