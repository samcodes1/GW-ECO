package com.rtechnologies.echofriend.utility;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.mysql.cj.xdevapi.Result;
import com.rtechnologies.echofriend.appconsts.AppConstants;

import io.jsonwebtoken.io.IOException;

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
        calendar.add(Calendar.DAY_OF_MONTH, numberofdays);

        // Convert calendar back to timestamp
        Timestamp newTimestamp = new Timestamp(calendar.getTimeInMillis());

        return newTimestamp;
    }

    public static java.sql.Date getcurrentDate(){
        LocalDate currentDate = LocalDate.now();
        return java.sql.Date.valueOf(currentDate);
    }

    public static Timestamp getcurrentTimeStamp(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Convert LocalDateTime to java.sql.Timestamp
        return Timestamp.valueOf(currentDateTime);
    }

    public static byte[] generateBarcode(String barcodeText, BarcodeFormat format, int width, int height) {
        try {
            // Generate the barcode image
            BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeText, format, width, height);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "png", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.err.println("Error generating barcode: " + e.getMessage());
            return null;
        }
    }

    public static String decodeBarcode(byte[] barcodeImage) throws java.io.IOException {
        try {
            // Convert byte array to BufferedImage
            ByteArrayInputStream inputStream = new ByteArrayInputStream(barcodeImage);
            java.awt.image.BufferedImage bufferedImage = ImageIO.read(inputStream);

            // Decode barcode using ZXing
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE); // Optional: Enable try-harder mode for better accuracy
            com.google.zxing.Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            return result.getText();
        } catch (IOException | NotFoundException e) {
            System.err.println("Error decoding barcode: " + e.getMessage());
            return null;
        }
    }

    // public static String generateBarcodeDigits(String barcodeText, BarcodeFormat format, int width, int height) {
    //     try {
    //         BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeText, format, width, height);
    //         int barcodeWidth = bitMatrix.getWidth();
    //         int barcodeHeight = bitMatrix.getHeight();
    //         StringBuilder barcodeDigits = new StringBuilder();
            
    //         // Determine the number of digits per group based on the barcode format
    //         int digitsPerGroup = (format == BarcodeFormat.CODE_128) ? 3 : 4;
    
    //         for (int y = 0; y < barcodeHeight; y++) {
    //             for (int x = 0; x < barcodeWidth; x += digitsPerGroup) {
    //                 int decimalValue = 0;
    //                 for (int i = 0; i < digitsPerGroup && x + i < barcodeWidth; i++) {
    //                     decimalValue = (decimalValue << 1) + (bitMatrix.get(x + i, y) ? 1 : 0);
    //                 }
    //                 barcodeDigits.append(decimalValue);
    //             }
    //         }
            
    //         return barcodeDigits.toString();
    //     } catch (WriterException e) {
    //         System.err.println("Error generating barcode digits: " + e.getMessage());
    //         return null;
    //     }
    // }

    public static Timestamp parseTimestamp(String timestampString) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        
        try {
            Date parsedDate = dateFormat.parse(timestampString);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            // Handle parsing exception
            e.printStackTrace();
            return null;
        }
    }

    public static String generateOTP() {
        // Generate a random 5-digit number
        Random random = new Random();
        int otpValue = 10000 + random.nextInt(90000);
        return String.valueOf(otpValue);
    }

    public static Timestamp getExpiryTimestampOneMinute() {
        // Get the current time
        LocalDateTime currentTime = LocalDateTime.now();
        
        // Add one minute to the current time
        LocalDateTime expiryTime = currentTime.plusMinutes(1);
        
        // Convert the LocalDateTime to a SQL Timestamp
        return Timestamp.valueOf(expiryTime);
    }

     public static String generateUniqueCode(Timestamp timestampparam) {
        // Get current timestamp in milliseconds
        long timestamp = timestampparam.toInstant().getEpochSecond();
        
        // Generate a UUID to ensure uniqueness
        String uniqueId = UUID.randomUUID().toString();
        
        // Combine timestamp and unique identifier
        String combinedData = timestamp + "-" + uniqueId;
        
        // Add some randomness (optional)
        // You can add more randomness if needed
        combinedData += Math.random();
        
        // Hash the combined data to get a fixed-length string
        String hash = md5Hash(combinedData);
        
        // Take the first 30 characters of the hash
        return hash.substring(0, 30);
    }

    private static String md5Hash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
