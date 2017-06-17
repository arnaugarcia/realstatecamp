package com.arnaugarcia.realstatecamp.service.util;

import com.arnaugarcia.realstatecamp.domain.Property;

import java.util.Random;

/**
 * Utility class for generating references for entities.
 */
public final class ReferenceUtil {


    private ReferenceUtil() {

    }

    /**
     * Generates a reference
     *
     * @return the generated reference
     */
    public static String generateReferenceProperty(Property property) {
        if (checkProperty(property)){
            return "REF - " + getRandomNumberInRange(0,1000);
        }

        return null;
    }
    private static Boolean checkProperty(Property property){
        if (property != null){
            return true;
        }else{
            return false;
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
