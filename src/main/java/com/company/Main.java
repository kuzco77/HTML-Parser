package com.company;

import com.HTMLFetch.VNMediaFetch;
import com.config.Config;
import com.entities.VasItemNews;
import com.entities.VasItemNewsType;
import com.logger.Logger;
import com.model.FileManager;
import com.thread.ResizeImageThread;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        transferArticleFromVNMedia2SQL();
//        resizeImageWithThread();
    }

    public static void resizeImageWithThread() {
        List<VasItemNewsType> listType = VasItemNewsType.getArticleType();
        int k = 0;
        for (VasItemNewsType type : listType) {
            ResizeImageThread resizeThread = new ResizeImageThread(type, 0, 100000, "Resize Thread No." + k);
            resizeThread.start();
            k++;
        }
    }

    public static void resizeImageFromDetail() {
        List<VasItemNews> listNew = new ArrayList<>();
        List<VasItemNewsType> listType = VasItemNewsType.getArticleType();
        for (int i = 0; i < listType.size(); i++) {
            int k = 1;
            int numberOfError = 0;
            Logger.log("Page = " + k);
            Logger.log("Searching in category: " + listType.get(i).name + "(" + listType.get(i).id + ")");
            List<VasItemNews> newListNew = VasItemNews.getArticle(listType.get(i).id, 0, 1);
            k++;
            Logger.log("Size of new list: " + newListNew.size());
            Logger.log("Size of listNew: " + listNew.size());

            newListNew = VNMediaFetch.resizeImageInDetail(newListNew);
        }
    }

    public static void transferArticleFromVNMedia2SQL() {
        List<VasItemNews> listNew = new ArrayList<>();
        List<VasItemNewsType> listType = VasItemNewsType.getArticleType();
        for (int i = 0; i < 1; i++) {
            int k = 1;
            int numberOfError = 0;
            while (numberOfError < 3) {
                Logger.log("Page = " + k);
                Logger.log("Searching in category: " + listType.get(i).name + "(" + listType.get(i).id + ")");
                List<VasItemNews> newListNew = VNMediaFetch.getListNew2(listType.get(i).id, k, (urlPicture, fileName) -> {
                    return FileManager.downloadFile(urlPicture, Config.IMAGE_STORE_PATH + "/" + fileName);
                });
                listNew.addAll(newListNew);
                k++;
                Logger.log("Size of new list: " + newListNew.size());
                Logger.log("Size of listNew: " + listNew.size());

                if (newListNew.size() == 0) {
                    numberOfError++;
                } else if (VasItemNews.checkItemExist(newListNew.get(newListNew.size() - 1))) {
                    VasItemNews.add2SQL(newListNew);
                    numberOfError = 3;
                    Logger.log("===== Duplicate!!! Skip to next category ======");
                } else {
                    VasItemNews.add2SQL(newListNew);
                    numberOfError = 0;
                    Logger.log("===== Successfully add this batch(10) to DB ======");
                }
            }

        }
    }

}
