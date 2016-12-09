package com.flame.datasource;

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
   // String rule="{\"select\":\"ul#pins>li\",\"num\":\"all\",\"tag\":\"a\",\"attr\":\"href\",\"tag\":\"img\",\"attr\":\"alt\"\"attr\":\"data-original\"}";

    public static List<Lady> getLadyCover(String url){
        List<Lady> list=new ArrayList<>();
        try {
            Document document=Jsoup.connect(url).get();
            Elements elements=document.select("ul#pins>li>a");
            for(Element element:elements){
                Lady lady=new Lady();
                lady.mUrl= element.attr("href");
                Element img= element.getElementsByTag("img").first();
                lady.mDes =img.attr("alt");
                lady.mThumbUrl=img.attr("data-original");
                list.add(lady);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getLadyNum(String url){
        int num=0;
        try {
            Document document=Jsoup.connect(url).get();
            Elements elements=document.select("div.pagenavi>a");
            int size=elements.size();
            Element a=elements.get(size-2);
            Element span= a.getElementsByTag("span").first();
            num=Integer.valueOf(span.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return num;
    }

    public static String getLadyImage(String baseUrl,int index){
        String src=null;
        try {
            String url=baseUrl+"/"+index;
            Document document=Jsoup.connect(url).get();
            Element element=document.select("div.main-image>p>a").first();
            Element img= element.getElementsByTag("img").first();
            src=img.attr("src");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return src;
    }

}
