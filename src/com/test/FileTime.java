package com.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FileTime {
	
	
	public static void main(String[] args) {          

        Date randomDate = randomDate("2013-10-01", "2013-10-31");

        SimpleDateFormat formata = new SimpleDateFormat("yyyyMMdd");

        System.out.println(formata.format(randomDate));

}



private static Date randomDate(String beginDate, String endDate) {

    try {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date start = format.parse(beginDate);// 开始日期

        Date end = format.parse(endDate);// 结束日期

        if (start.getTime() >= end.getTime()) {

            return null;

        }

        long date = random(start.getTime(), end.getTime());



        return new Date(date);

    } catch (Exception e) {

        e.printStackTrace();

    }

    return null;

}



private static long random(long begin, long end) {

    long rtnn = begin + (long) (Math.random() * (end - begin));

    if (rtnn == begin || rtnn == end) {

        return random(begin, end);

    }

    return rtnn;

  }

}