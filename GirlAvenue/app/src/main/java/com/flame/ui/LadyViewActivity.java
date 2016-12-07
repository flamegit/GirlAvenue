package com.flame.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.flame.datasource.Fetcher;
import com.flame.datasource.RemoteLadyFetcher;
import com.flame.model.ShowDetailEvent;
import com.flame.presenter.GirlPresenter;
import com.flame.utils.RxBus;

import rx.Subscription;
import rx.functions.Action1;

public class LadyViewActivity extends AppCompatActivity {

    Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        if(intent==null){
            return;
        }
        String url=intent.getStringExtra("url");
        String desc=intent.getStringExtra("desc");
        Log.d("fxlts",url);
        LadyPreViewFragment ladyPreViewFragment;
        Fragment fragment= getSupportFragmentManager().findFragmentById(android.R.id.content);
        if(fragment!=null && fragment instanceof LadyPreViewFragment){
            ladyPreViewFragment=(LadyPreViewFragment)fragment;
        }else {
            ladyPreViewFragment=LadyPreViewFragment.Instance(url,desc);
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, ladyPreViewFragment)
                    .commit();
        }
        new GirlPresenter(ladyPreViewFragment,RemoteLadyFetcher.getInstance());

        mSubscription= RxBus.getDefault().toObservable(ShowDetailEvent.class).subscribe(new Action1<ShowDetailEvent>() {
            @Override
            public void call(ShowDetailEvent event) {
                showPageFragment(event.url,event.index);
            }
        });

    }

    private void showPageFragment(String url,int index){
        GirlPageFragment fragment=GirlPageFragment.Instance(url,index);
        new GirlPresenter(fragment, RemoteLadyFetcher.getInstance());
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content,fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       if( mSubscription!=null&& !mSubscription.isUnsubscribed()){
           mSubscription.unsubscribe();
       }
    }
}
