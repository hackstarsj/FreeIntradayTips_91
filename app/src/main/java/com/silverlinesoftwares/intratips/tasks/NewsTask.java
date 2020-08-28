package com.silverlinesoftwares.intratips.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.silverlinesoftwares.intratips.adapters.NewsAdapter;
import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;
import com.silverlinesoftwares.intratips.listeners.NewsListener;
import com.silverlinesoftwares.intratips.models.NewsModel;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

public class NewsTask extends AsyncTask<String,String,String> {

    String keyword;
    NewsListener gainerLooserListener;
    private List<NewsModel> listNews=new ArrayList<>();
    int totosl;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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

    @Override
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null) {
            if(!s.isEmpty()) {
                Document jsoup = Jsoup.parse(s);
                Elements data = jsoup.getElementsByClass("ZINbbc");
                for (Element element : data) {
                    try {
                        Elements title = element.getElementsByClass("vvjwJb");
                        Elements desc = element.getElementsByClass("s3v9rd");
                        Elements links = element.getElementsByTag("a");
                        Elements dates = element.getElementsByClass("rQMQod");

                        Elements id = element.getElementsByClass("EYOsld");

                        String url = links.get(0).attr("href").replace("/url?q=", "");
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

//        @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//       // webView.loadData(s,"text/html","UTF-8");
//        if(s!=null){
//            final String regex = "<div class=\"ZINbbc xpd O9g5cc uUPGi\">(.*?)<div>";;
//            final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
//            final Matcher matcher = pattern.matcher(s);
//
//            final String regex2 = "<div class=\"st\">(.*?)<\\/div>";
//            final Pattern pattern2 = Pattern.compile(regex2, Pattern.MULTILINE);
//            final Matcher matcher2 = pattern2.matcher(s);
//
//            final String regex3 = "<img class=\"th\" src=\"(.*?)\"";
//            final Pattern pattern3 = Pattern.compile(regex3, Pattern.MULTILINE);
//            final Matcher matcher3 = pattern3.matcher(s);
//
//
//
//
//
//            while (matcher.find()) {
//                System.out.println("Full match: " + matcher.group(0));
//                for (int i = 1; i <= matcher.groupCount(); i++) {
//                    System.out.println("Group " + i + ": " + matcher.group(i));
//                  //  textView.append(matcher.group(i)+"\n\n");
//                    if(matcher2.find()){
//
//                        if(matcher3.find()) {
//                            BuildData(matcher.group(i), matcher2.group(0), matcher3.group(1));
//                        }
//                        else{
//                            BuildData(matcher.group(i), matcher2.group(0),"");
//
//                        }
//                    }
//                    else {
//                        BuildData(matcher.group(i),"","");
//                    }
//
//                }
//            }
//
//            ShowDataList();
//
//        }
//        else {
//            gainerLooserListener.onFailedNews("Something Went Wrong!");
//        }
//
//
//    }
//
//    private void ShowDataList() {
//        gainerLooserListener.onSucess(listNews);
//    }
//
//    private void BuildData(String s,String d,String  im){
//        Log.d("text",s);
//        String link="",img="",title="",dates="",desc="";
//        final String regex = "href=\"(.*?)\"";
//        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
//        final Matcher matcher = pattern.matcher(s);
//
//        while (matcher.find()) {
//            System.out.println("Full match: " + matcher.group(0));
//            for (int i = 1; i <= matcher.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher.group(i));
//                link=matcher.group(i);
//                break;
//            }
//            break;
//        }
//
//        final String regex1 = "src=\"(.*?)\"";
//        final Pattern pattern1 = Pattern.compile(regex1, Pattern.MULTILINE);
//        final Matcher matcher1 = pattern1.matcher(s);
//
//        while (matcher1.find()) {
//            System.out.println("Full match: " + matcher1.group(0));
//            for (int i = 0; i <= matcher1.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher1.group(i));
//                img=matcher1.group(i);
//                break;
//            }
//            break;
//        }
//
//        final String regex2 = "<a href=\"(.*?)\">(.*?)<\\/a>";
//        //final String regex2 = "<a class=\"l lLrAF\" href=\"(.*?)\" onmousedown=\"(.*?)\">(.*?)<\\/a>";
//        final Pattern pattern2 = Pattern.compile(regex2, Pattern.MULTILINE);
//        final Matcher matcher2 = pattern2.matcher(s);
//
//        while (matcher2.find()) {
//            System.out.println("Full match: " + matcher2.group(0));
//            for (int i = 1; i <= matcher2.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher2.group(i));
//                title=matcher2.group(i);
//               // break;
//            }
//            break;
//        }
//        final String regex3 = " -(.*?)<\\/span>";
//        //final String regex3 = "<span class=\"f nsa fwzPFf\">(.*?)<\\/span>";
//        final Pattern pattern3 = Pattern.compile(regex3, Pattern.MULTILINE);
//        final Matcher matcher3 = pattern3.matcher(s);
//
//        while (matcher3.find()) {
//            System.out.println("Full match: " + matcher3.group(0));
//            for (int i = 1; i <= matcher3.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher3.group(i));
//                dates=matcher3.group(i);
//                break;
//            }
//            break;
//        }
//
//        final String regex4 = "<div class=\"st\">(.*?)<\\/div>";
//        final Pattern pattern4 = Pattern.compile(regex4, Pattern.MULTILINE);
//        final Matcher matcher4 = pattern4.matcher(s);
//
//        while (matcher4.find()) {
//            System.out.println("Full match: " + matcher4.group(0));
//            for (int i = 1; i <= matcher4.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher4.group(i));
//                desc=matcher4.group(i);
//                break;
//            }
//            break;
//        }
//
//
//        final String regex6 = "q=(.*?)&";
//
//        final Pattern pattern6 = Pattern.compile(regex6, Pattern.MULTILINE);
//        final Matcher matcher6 = pattern6.matcher(link);
//
//        while (matcher6.find()) {
//            System.out.println("Full match: " + matcher6.group(0));
//            link=matcher6.group(1);
//
//        }
//
//        listNews.add(new NewsModel(html2text(title),link,im,html2text(d),html2text(dates)));
//
//    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
