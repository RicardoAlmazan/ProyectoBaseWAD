package com.ipn.mx.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    public static java.sql.Date convertDate(String date) {
        java.util.Date dateToConvert;
        try {
            dateToConvert = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(date);
            return convert(dateToConvert);
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}
