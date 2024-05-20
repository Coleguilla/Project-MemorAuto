package com.example.memorauto.db.database;

import androidx.room.TypeConverter;

import java.util.GregorianCalendar;

public class Converters {

    @TypeConverter
    public static GregorianCalendar fromTimestamp(Long value) {
        if (value == null) {
            return null;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(value);
            return gc;
        }
    }

    @TypeConverter
    public static Long dateToTimestamp(GregorianCalendar date) {
        return date == null ? null : date.getTimeInMillis();
    }

}
