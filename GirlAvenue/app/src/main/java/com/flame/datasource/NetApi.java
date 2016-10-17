package com.flame.datasource;

import java.util.Observable;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/10/3.
 */

public interface NetApi {

    @GET("data/福利/10/{page}")
    Observable getGirlList(@Path("page")int page);
}
