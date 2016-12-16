package com.flame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
        setContentView(R.layout.activity_lady_view);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        String desc=intent.getStringExtra("desc");
        LadyPreViewFragment ladyPreViewFragment;
        Fragment fragment= getSupportFragmentManager().findFragmentById(R.id.content);
        if(fragment!=null && fragment instanceof LadyPreViewFragment){
            ladyPreViewFragment=(LadyPreViewFragment)fragment;
        }else {
            ladyPreViewFragment=LadyPreViewFragment.Instance(url,desc);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, ladyPreViewFragment)
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
                //.setTransition()
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
