package com.flame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.flame.datasource.LocalGirlFetcher;
import com.flame.datasource.RemoteLadyFetcher;
import com.flame.model.ShowDetailEvent;
import com.flame.presenter.GirlPresenter;
import com.flame.utils.RxBus;

import rx.Subscription;
import rx.functions.Action1;

public class LadyViewActivity extends AppCompatActivity {
    Subscription mSubscription;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lady_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mSubscription = RxBus.getDefault().toObservable(ShowDetailEvent.class).subscribe(new Action1<ShowDetailEvent>() {
            @Override
            public void call(ShowDetailEvent event) {
                showPageFragment(event.url, event.index);
            }
        });

        Intent intent = getIntent();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        GirlListFragment listFragment;
        if (intent.getAction() != null && intent.getAction().equals("favorite")) {
            if (fragment == null) {
                listFragment = new GirlListFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.content, listFragment)
                        .commit();
            }
            return;
        }
        String url = intent.getStringExtra("url");
        String desc = intent.getStringExtra("desc");
        LadyPreViewFragment ladyPreViewFragment;
        if (fragment == null) {
            ladyPreViewFragment = LadyPreViewFragment.Instance(url, desc);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, ladyPreViewFragment)
                    .commit();
        }
    }

    public void showToolbar(boolean show){
        if(show){
            mToolbar.setVisibility(View.VISIBLE);
        }else {
           // mToolbar.setVisibility(View.GONE);
            mToolbar.setBackgroundResource(android.R.color.black);
        }
    }

    private void showPageFragment(String url, int index) {
        GirlPageFragment fragment = GirlPageFragment.Instance(url, index);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
