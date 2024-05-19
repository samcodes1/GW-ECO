package com.efrcs.echofriend.utility;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

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
}
