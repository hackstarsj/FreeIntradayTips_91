package com.silverlinesoftwares.intratips.tasks;

import android.os.Handler;
import android.os.Looper;

import com.silverlinesoftwares.intratips.listeners.NewsListener;
import com.silverlinesoftwares.intratips.models.NewsModel;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsTask {

    final String keyword;
    final NewsListener gainerLooserListener;
    private final List<NewsModel> listNews=new ArrayList<>();
    final int totosl;

    public void execute(String... strings) {
        Executor executor = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            String data=doInBackground(strings);
            handler.post(()-> onPostExecute(data));
        });
    }


    public NewsTask(NewsListener gainerLooserListener,String keyword,int length){
        this.gainerLooserListener=gainerLooserListener;
        this.totosl=length;
        if(keyword==null){
            this.keyword="Stock News";
        }
        else{
            this.keyword=keyword;
        }
    }

    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        Request request=null;
        if(totosl==0) {
            request =
                    new Request.Builder()
                            .addHeader("User-Agent","Opera/9.80 (Android; Opera Mini/11.0.1912/37.7549; U; pl) Presto/2.12.423 Version/12.16")
                            .url("https://www.google.com/search?q=" + keyword + "&tbm=nws&sa=X&num=50")
                            .build();
        }
        else{
            request =
                    new Request.Builder()
                            .addHeader("User-Agent","Opera/9.80 (Android; Opera Mini/11.0.1912/37.7549; U; pl) Presto/2.12.423 Version/12.16")
                            .url("https://www.google.com/search?q=" + keyword + "&tbm=nws&sa=X&num="+totosl)
                            .build();
        }

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (response != null && response.isSuccessful()) {
            try {
                if (response.body() != null) {
                    return response.body().string();
                }
                else{
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    protected void onPostExecute(String s) {
        if(s!=null) {
            if(!s.isEmpty()) {
                Document jsoup = Jsoup.parse(s);
                Elements data = jsoup.getElementsByClass("ZINbbc");
                for (Element element : data) {
                    try {
                        Elements title = element.getElementsByClass("vvjwJb");
                        Elements desc = element.getElementsByClass("s3v9rd");
                        Elements links = element.getElementsByTag("a");
                        Elements dates = element.getElementsByClass("UMOHqf");

                        Elements id = element.getElementsByClass("h1hFNe");

                        String[] urls = links.get(0).attr("href").replace("/url?q=", "").split("&amp;sa");
                        if(urls.length==1){
                            urls=links.get(0).attr("href").replace("/url?q=", "").split("&sa");
                        }
                        String url=urls[0];

                        String img = getImages(s, id.get(0).attr("id"));
                        NewsModel newsModel = new NewsModel(title.text(), url, img, desc.get(0).text(), dates.get(0).text());
                        newsModel.setImages(img);
                        listNews.add(newsModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                gainerLooserListener.onSucess(listNews);
            }
            else{
                gainerLooserListener.onFailedNews("Failed to Load News");
            }
        }
        else{
            gainerLooserListener.onFailedNews("Failed to Load News");
        }
    }

    private String getImages(String html,String id) {
        String data="";
        //final String regex1 = "var i=\\['DIMG_PLACE'\\];_setImagesSrc\\(i,s\\);\\}\\)\\(\\);\\(function\\(\\)\\{var s='data:image\\/jpeg;base64,(.*?)'";
        final String regex1 = "var i=\\['DIMG_PLACE'\\];_setImagesSrc\\(i,s\\);\\}\\)\\(\\);<\\/script><script nonce=\"(.*?)==\">\\(function\\(\\)\\{var s='data:image\\/jpeg;base64,(.*?)'";
        //final  String regex1="var i=\\['DIMG_PLACE'\\];_setImagesSrc\\(i,s\\);\\}\\)\\(\\);\\(function\\(\\)\\{var s='data:image\\/jpeg;base64,(.*?)'";
        final String regex=regex1.replace("DIMG_PLACE",id);
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {

                System.out.println("Group " + i + ": " + matcher.group(i));
                data=matcher.group(i);
              //  break;
            }
            break;
        }
        return data;
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
