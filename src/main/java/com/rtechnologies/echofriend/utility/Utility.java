package com.rtechnologies.echofriend.utility;

import java.util.Calendar;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.rtechnologies.echofriend.appconsts.AppConstants;

public class Utility {
    public static String mapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey())
              .append("=")
              .append(entry.getValue())
              .append(", ");
        }
        if (!map.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length()); // Remove trailing comma and space
        }
        sb.append("}");
        return sb.toString();
    }

    public static Timestamp convertToTimestamp(String dateTimeString) throws java.text.ParseException {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = dateFormat.parse(dateTimeString);
        return new Timestamp(parsedDate.getTime());
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        String saltedPassword = password + AppConstants.SALT;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(saltedPassword.getBytes());

        return bytesToHex(hashBytes);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean compareHashes(String hash1, String hash2) {
        return hash1.equals(hash2);
    }

    public static java.sql.Date stringToSqlDate(String dateStr) throws ParseException{
        return new java.sql.Date(
            ((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(dateStr)).getTime());
    }

    public static Timestamp getDaysExpiryFromCurrentDate(int numberofdays){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Convert timestamp to calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());

        // Add 7 days to the calendar
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        // Convert calendar back to timestamp
        Timestamp newTimestamp = new Timestamp(calendar.getTimeInMillis());

        return newTimestamp;
    }
}
