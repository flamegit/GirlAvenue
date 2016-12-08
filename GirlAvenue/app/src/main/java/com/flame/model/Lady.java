package com.flame.model;

import java.util.List;

/**
 * Created by Administrator on 2016/10/15.
 */
public class Lady {
   public String mDes;
   public String mThumbUrl;
   public String mUrl;
   public List<String> mList;

   @Override
   public boolean equals(Object o) {
      if(o instanceof Lady){
        return mUrl.equals(((Lady) o).mUrl);
      }
      return false;
   }
}
