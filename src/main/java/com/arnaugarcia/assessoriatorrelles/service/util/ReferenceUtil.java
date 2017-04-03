package com.arnaugarcia.assessoriatorrelles.service.util;

import com.arnaugarcia.assessoriatorrelles.domain.Location;
import com.arnaugarcia.assessoriatorrelles.domain.Property;

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
    public static String generateReferenceProperty(Property property, Location Location) {
        if (checkProperty(property)){
            return "REF-117";
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

}
