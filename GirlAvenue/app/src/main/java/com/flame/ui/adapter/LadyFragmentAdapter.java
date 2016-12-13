package com.flame.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.flame.datasource.RemoteLadyFetcher;
import com.flame.presenter.GirlPresenter;
import com.flame.ui.GirlListFragment;

/**
 * Created by Administrator on 2016/8/13.
 */
public class LadyFragmentAdapter extends FragmentPagerAdapter {

    String[][] titles={{"最新","最热","推荐"},{"性感妹子", "日本妹子", "台湾妹子", "清纯妹子"}};
    String[][] paths={{"","hot/","best/"},{"xinggan/","japan/","taiwan/","mm/"}};

    int type;


    public LadyFragmentAdapter(FragmentManager fm, int type) {
        super(fm);
        this.type=type;
    }

    @Override
    public CharSequence getPageTitle(int position) {
       // return super.getPageTitle(position);
        return titles[type][position];
    }

    @Override
    public int getCount() {
        return titles[type].length;
    }


    @Override
    public Fragment getItem(int position) {
        GirlListFragment fragment= new GirlListFragment();
        new GirlPresenter(fragment,RemoteLadyFetcher.getInstance()).setBaseUrl(paths[type][position]);
        return fragment;
    }


}
