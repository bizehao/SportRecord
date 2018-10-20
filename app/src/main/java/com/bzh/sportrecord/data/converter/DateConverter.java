package com.bzh.sportrecord.data.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * @author 毕泽浩
 * @Description: date类型转换
 * @time 2018/10/20 11:20
 */
public class DateConverter {

    @TypeConverter
    public static Date reverDate(Long value){
        return new Date(value);
    }

    @TypeConverter
    public static Long converterDate(Date value){
        return value.getTime();
    }
}
