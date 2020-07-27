package com.logger;

import com.config.Config;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public Logger() {
    }

    public static void log(String log) {
        System.out.println(log);
        String date = (new SimpleDateFormat("yyyyMMdd")).format(new Date());

        try {
            try {
                String path = Config.LOG_PATH;
                if (path.charAt(path.length() - 1) != '/') {
                    path = path + "/";
                }

                File file = new File(path + date + ".log");
                if (!file.exists() && !file.createNewFile()) {
                    return;
                }

                String sdf = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());

                try {
                    FileWriter writer = new FileWriter(file, true);
                    String logMsg = log + "\r\n";
                    logMsg = "[" + sdf + "] " + logMsg;
                    writer.write(logMsg);
                    writer.flush();
                    writer.close();
                } catch (Exception var11) {
                }
            } catch (IOException var12) {
            }

        } finally {
        }
    }

    public static void error(Exception ex) {
        ex.printStackTrace();
        String date = (new SimpleDateFormat("yyyyMMdd")).format(new Date());

        try {
            try {
                String path = Config.LOG_PATH;
                Writer buffer = new StringWriter();
                PrintWriter pw = new PrintWriter(buffer);
                ex.printStackTrace(pw);
                if (path.charAt(path.length() - 1) != '/') {
                    path = path + "/";
                }

                File file = new File(path + date + ".log");
                if (!file.exists() && !file.createNewFile()) {
                    return;
                }

                String sdf = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());

                try {
                    FileWriter writer = new FileWriter(file, true);
                    String logMsg = "[ERROR] " + buffer.toString() + "\r\n";
                    logMsg = "[" + sdf + "] " + logMsg;
                    writer.write(logMsg);
                    writer.flush();
                    writer.close();
                    pw.close();
                    buffer.close();
                } catch (Exception var13) {
                }
            } catch (IOException var14) {
            }

        } finally {
        }
    }
}