package com.grocero.common;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static LocalDateTime getTokenValidity() {
        return LocalDateTime.now().plus(60, ChronoUnit.SECONDS);
    }

}
