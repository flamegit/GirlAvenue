package com.flame.model;

/**
 * Created by Administrator on 2016/10/15.
 */
public class ShowDetailEvent {
   public String  url;
   public int index;

   public  ShowDetailEvent(String url,int index){
      this.url=url;
      this.index=index;
   }

}
