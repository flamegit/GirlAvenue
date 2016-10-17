package com.flame.datasource;

import android.util.Log;

import com.flame.model.Lady;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */
public class HtmlParse {
    public static List<Lady> getHtmlContent(String url){
        List<Lady> list=new ArrayList<>();
        try {
            Document document=Jsoup.connect(url).get();
            Elements uls=document.select("ul[id=pins]");
            if(uls.size()!=1){
                Log.d("fxlts","not found");
            }
            for(Element element:uls.get(0).getElementsByTag("li")){
               list.add(parseTag(element));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    private static Lady parseTag(Element element){
        Lady lady=new Lady();
        lady.mUrl= element.attr("href");
        Element img= element.getElementsByTag("img").get(0);
        lady.mDes =img.attr("alt");
        lady.mThumbUrl=img.attr("data-original");
        return lady;
    }
}
