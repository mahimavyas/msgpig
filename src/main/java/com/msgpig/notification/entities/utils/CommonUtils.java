package com.msgpig.notification.entities.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    private static final Gson gson = new Gson();

    static DateFormat df = new SimpleDateFormat("dd MMM");

    static DateFormat dfTime = new SimpleDateFormat("hh:mm a");

    public static Date getDateFromLong(Long date)
    {
        return new Date(date);

    }
    
    public static String getFileStrWithUtil(String fileName) {

		String result = "";
		ClassLoader classLoader = CommonUtils.class.getClassLoader();
		try {
			result = IOUtils
					.toString(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
    
	public static Long addDaysToDateLong(Long date, int days) {
		return date + (days * 1000L * 24 * 60 * 60);
	}

    public static InputStream getFileWithUtil(String fileName) {

        ClassLoader classLoader = CommonUtils.class.getClassLoader();
        try {
            return classLoader.getResourceAsStream(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getDateInMMDDYYYYFromLong(Long date)
    {
        if (date != null)
        {
            Date dateObj = new Date(date);
            if (dateObj != null)
            {
                return dfTime.format(date) + " on " + df.format(dateObj);
            }
        }
        return "";

    }

    public static String getOfferingByProvider(String providerType)
    {
        if (providerType != null)
        {
            if (providerType.equals("PHARMACY"))
            {
                return "medicines";
            }
            else if (providerType.equals("LAB"))
            {
                return "tests";
            }
        }
        return "";

    }
    
    public static String getProviderTextProvider(String providerType)
    {
        if (providerType != null)
        {
            if (providerType.equals("PHARMACY"))
            {
                return "pharmacies";
            }
            else if (providerType.equals("LAB"))
            {
                return "labs";
            }
        }
        return "";

    }

    public static Double getDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0D;
        }
    }

    public static Double calculateDistanceBetweenPoints(Double lng1, Double lat1, Double lng2, Double lat2) {
        if (lng1 != null && lat1 != null && lng2 != null && lat2 != null) {
            double theta = lng1 - lng2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                    + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = (dist * 60 * 1.1515) * 1.609344;
            return dist;
        }
        return null;
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static Set<String> fillDatesFromTo(Long startDate, Long endDate) {
        Set<String> dates = new LinkedHashSet<>();
        if (startDate != null && endDate != null) {
            Long sDate = getDateWithZeroTime(startDate);
            Long eDate = getDateWithZeroTime(endDate);
            long diffInDays = (eDate - sDate) / (24 * 60 * 60 * 1000);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(sDate);
            for (int i = 0; i <= diffInDays; i++) {
                cal.add(Calendar.DATE, i);
                dates.add(timestampToDate(cal.getTimeInMillis(), "yyyy-MM-dd"));
                cal.setTimeInMillis(sDate);
            }
        }
        return dates;
    }

    public static Long getDateWithZeroTime(Long dat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dat);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime().getTime();
    }

    public static Integer getTime(String strTime)
    {
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        try {
            Date date = parseFormat.parse(strTime);
            return date.getHours() * 60 * 60 + date.getMinutes() * 60;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public static String timestampToDate(Long timestamp, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String date = sdf.format(timestamp);
            return date.toString();
        } catch (Exception e) {
        }
        return null;
    }

    // This will return the values from keyAsString which contain pipe seperated string
    public static String getValueFromKeyAsString(String keyAsString, int value) {
        if (keyAsString != null && keyAsString.contains("|")) {
            String[] keyString = StringUtils.delimitedListToStringArray(keyAsString, "|");
            if (keyString != null && keyString.length > value && !keyString[value].equals("null")) {

                return keyString[value];
            }
        }
        return null;
    }

    public static long getLong(Double number) {
        try {
            return number.longValue();
        } catch (Exception e) {
            return 0L;
        }
    }

    public static long getLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (Exception e) {
            return -1;
        }
    }

    public static long getLong(String number, long _default) {
        try {
            return Long.parseLong(number);
        } catch (Exception e) {
            return _default;
        }
    }

    public static int getInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getTransformedMerchantId(String merchantId, String catId) {
        StringBuilder sb = new StringBuilder();
        if (merchantId != null && catId != null) {
            return sb.append(catId).append(merchantId).toString();
        }
        return null;
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static long convertDaysToMillis(long days) {
        return days * 24 * 60 * 60 * 1000;
    }

    public static long convertMinutesToMillis(long minutes) {
        return minutes * 60 * 1000;
    }

    public static Integer getDayOfWeekOfToday() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        return getDayOfWeekOf(calendar.getTimeInMillis());
    }

    public static Integer getDayOfWeekOf(long millis) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static Integer getDayOfWeek(Calendar calendar) {
        calendar.setTimeInMillis(calendar.getTimeInMillis());
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static long getCurrentTimeInMillis() {
        return System.currentTimeMillis();
    }

    public static Long getTodayDateInLong() {
        return LocalDate.now().atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    }

    public static String getTodayDate() {

        LocalDate date = LocalDate.now();
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    public static Double trimDouble(Double value) {
        if (value != null) {
            DecimalFormat twoDForm = new DecimalFormat("#.###");
            return Double.valueOf(twoDForm.format(value));
        }
        return null;
    }

}
