package com.HTMLFetch;

import com.config.Config;
import com.entities.GenericResponse;
import com.entities.VasItemNews;
import com.entities.VasItemNewsType;
import com.google.gson.Gson;
import com.logger.Logger;
import com.tools.Tools;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.config.Config.BASE_IMAGE_VNMEDIA_URL;


public class VNMediaFetch {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static List<VasItemNews> getListNew2(int typeInt, int page, VNMediaCompletion completion) {
        Document doc = null;
        List<VasItemNews> listNew = new ArrayList<>();
        int pageInAPI = page * 10 - 8;
        VasItemNewsType newType = VasItemNewsType.getArticleTypeById(typeInt);
        try {
            String apiLink = "http://vnmedia.vn/service/api/article/list?scUnitMapId=8a10a0d36ccebc89016ce0c6fa3e1b83&pageSize=10&saArticleCateId=" + newType.api_id + "&first=" + pageInAPI;
            return sendGet(typeInt, apiLink, completion);
        } catch (Exception e) {
            Logger.error(e);
        }
        return listNew;
    }

    private static void close() throws IOException {
        httpClient.close();
    }

    public static List<VasItemNews> resizeImageInDetail(List<VasItemNews> listArticle) {
        Document doc = null;
        List<VasItemNews> newListArticle = new ArrayList<>();
        for (int i = 0; i < listArticle.size(); i++) {
            VasItemNews newArticle = listArticle.get(i);
            doc = Jsoup.parseBodyFragment(newArticle.detail);
            Elements imgElements = doc.select("img").attr("width", "500");
            for (Element imgElement: imgElements) {
                imgElement.attr("width", "100%");
            }
            newArticle.detail = doc.toString();
            newListArticle.add(newArticle);
        }
        return newListArticle;
    }

    private static List<VasItemNews> sendGet(int typeInt, String apiURL, VNMediaCompletion completion) throws Exception {
        Document doc = null;
        List<VasItemNews> listNews = new ArrayList<>();
        HttpGet request = new HttpGet(apiURL);

        // add request headers
        request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A");

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                Gson gson = new Gson();

                GenericResponse res = gson.fromJson(result, GenericResponse.class);

                for (int i = 0; i < res.listItem.size(); i++) {
                    VasItemNews item = new VasItemNews();
                    item.url = Config.BASE_VNMEDIA_URL + res.listItem.get(i).pageUrl;
                    item.type = typeInt;
                    item.title = res.listItem.get(i).title;
                    item.status = 0;
                    item.shortDesc = Jsoup.parse(res.listItem.get(i).lead).text().replace("(VnMedia) -", "");

                    item.create_date = Tools.convertString2Date(res.listItem.get(i).publishDate, Config.VNMEDIA_API_DATE_FORMAT);

                    String logoLink = res.listItem.get(i).avatar;
                    String avatar = logoLink.contains("?") ? logoLink.substring(0, logoLink.indexOf("?")) : logoLink;
                    item.logo = avatar.substring(avatar.lastIndexOf("/") + 1);

                    doc = Jsoup.connect(item.url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A").get();
                    Elements detailElements = doc.getElementsByClass("td-post-content tagdiv-type");
                    detailElements.select("img.logo").remove();
                    detailElements.select("div.related").remove();
                    String detail;
                    detail = detailElements.size() > 0 ? detailElements.first().html() : "";
                    item.detail = detail;

                    listNews.add(item);

                    completion.downloadFunc(BASE_IMAGE_VNMEDIA_URL + avatar, item.logo);

//                    Logger.log("Image: " + BASE_IMAGE_VNMEDIA_URL + avatar);
//                    Logger.log("File name: " + item.logo);
//                    Logger.log("Title: " + item.title);
//                    Logger.log("Date: " + item.create_date);
//                    Logger.log("Type: " + typeInt);
//
//                    Logger.log("Summary :" + item.shortDesc);
//                    Logger.log("Href: " + item.url);
//                    Logger.log("Detail: " + item.detail);
                }
            }


        } catch (Exception e) {
            Logger.error(e);
        }

        return listNews;
    }



    public interface VNMediaCompletion {
        boolean downloadFunc(String urlPicture, String fileName);
    }
}
