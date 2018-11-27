package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Tools {

    public static String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH:mm");
        LocalDateTime dateTime = LocalDateTime.now();

        return dateTime.format(formatter);
    }

    public static String getRandomNumber(int i) {
        Random r = new Random();
        return String.valueOf(r.nextInt(i));
    }
}

