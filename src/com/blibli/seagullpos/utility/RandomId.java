package com.blibli.seagullpos.utility;

import java.security.SecureRandom;
import java.util.Random;

public class RandomId {
    private static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static String DIGIT = "0123456789";

    private static final Random random = new SecureRandom();

    private static char getChar(){
        return ALPHABET.charAt(random.nextInt(ALPHABET.length()));
    }

    private static char getDigit(){
        return DIGIT.charAt(random.nextInt(DIGIT.length()));
    }

    public static long generateRandomNumber(int length){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < length; i++){
            char digit = getDigit();
            if(i == 0 && digit == '0'){
                i--;
                System.out.println(digit);
                continue;
            }
            sb.append(digit);
        }

        String newId = sb.toString();
        System.out.println("String : " + newId);
        return Long.parseLong(newId);
    }

    public static String generateProductId(int length, int spacingLength, char spacingChar){
        StringBuilder sb = new StringBuilder();

        for(int i = 1; i <= length; i++){
            sb.append(getChar());
            if(i % spacingLength == 0 && i != length){
                sb.append(spacingChar);
            }
        }
        return sb.toString();
    }
}
