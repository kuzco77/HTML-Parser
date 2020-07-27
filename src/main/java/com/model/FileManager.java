package com.model;

import com.logger.Logger;
import org.apache.commons.io.FileUtils;
import sun.rmi.runtime.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileManager {
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 10000;

    public static boolean downloadFile(String url, String outputPath) {
        try {
            FileUtils.copyURLToFile(
                    new URL(url),
                    new File(outputPath),
                    CONNECTION_TIMEOUT,
                    READ_TIMEOUT
            );
        } catch (IOException e) {
            Logger.error(e);
        }
        return false;
    }
}