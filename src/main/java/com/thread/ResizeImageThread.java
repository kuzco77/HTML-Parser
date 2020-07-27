package com.thread;

import com.HTMLFetch.VNMediaFetch;
import com.entities.VasItemNews;
import com.entities.VasItemNewsType;
import com.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class ResizeImageThread implements Runnable {
    private Thread t;
    private final String threadName;
    private final VasItemNewsType type;
    private final int offset;
    private final int limit;

    public ResizeImageThread(VasItemNewsType type, int offset, int limit, String name) {
        threadName = name;
        this.type = type;
        this.offset = offset;
        this.limit = limit;
    }

    public void run() {
        System.out.println("Running " + threadName);
        try {
            resizeImageFromDetail();
            // Let the thread sleep for a while.
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted.");
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    private void resizeImageFromDetail() {
        List<VasItemNews> listNew = new ArrayList<>();
        Logger.log("Searching in category: " + type.name + "(" + type.id + ")");
        List<VasItemNews> newListNew = VasItemNews.getArticle(type.id, offset, limit);

        newListNew = VNMediaFetch.resizeImageInDetail(newListNew);
        VasItemNews.updateArticleSQL(newListNew);
    }
}