package com.flame.presenter;

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

    }
    interface Presenter{
        void getGirlList();
        void start();
        void getNext();
    }
}
