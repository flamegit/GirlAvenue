package com.flame.presenter;

import com.flame.model.Lady;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public interface GirlContract {

    interface View{
        void setPresenter(Presenter presenter);
        void showProgress();
        void hideProgress();
        void fillView(List results);
        void fillView(String item);

    }
    interface Presenter{
        void getGirlList();
        void start();
        void getNext();
        void getLadyImages(String url);
        void cancel();

    }
}
