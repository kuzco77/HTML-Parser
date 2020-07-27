package com.tools;

import com.logger.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    public static Date convertString2Date(String dateString, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            Logger.error(e);
        }
        return null;
    }
}
