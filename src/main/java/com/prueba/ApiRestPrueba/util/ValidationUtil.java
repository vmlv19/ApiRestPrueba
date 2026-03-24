package com.prueba.ApiRestPrueba.util;

public class ValidationUtil {

    private static final String RFC = "^[A-ZÑ&]{3,4}[0-9]{6}[A-Z0-9]{3}$";
    private static final String PHONE = "^(\\+?[0-9]{1,3}[\\s-]?)?[0-9]{2,3}[\\s-]?[0-9]{3}[\\s-]?[0-9]{3}[\\s-]?[0-9]{2}$";

    public static boolean isValidRFC(String taxId) {
        if (taxId == null || taxId.isEmpty()) {
            return false;
        }
        return taxId.matches(RFC);
    }

    public static boolean isValidPhone(String phone) {
        if(phone == null || phone.isEmpty()) {
            return false;
        }

        String onlyDigits = phone.replaceAll("[^0-9]", "");

        if(onlyDigits.length() < 10) {
            return false;
        }

        return phone.matches(PHONE);
    }
}