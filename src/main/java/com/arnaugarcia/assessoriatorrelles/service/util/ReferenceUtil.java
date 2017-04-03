package com.arnaugarcia.assessoriatorrelles.service.util;

import com.arnaugarcia.assessoriatorrelles.domain.Location;
import com.arnaugarcia.assessoriatorrelles.domain.Property;

/**
 * Utility class for generating random Strings.
 */
public final class ReferenceUtil {


    private ReferenceUtil() {
    }

    /**
     * Generates a password.
     *
     * @return the generated password
     */
    public static String generateReferenceProperty(Property property, Location Location) {

        if (checkProperty(property)){
            return "REF-117";
        }

        return null;
    }
    private static Boolean checkProperty(Property property){
        if (!property.equals(null)){
            return true;
        }else{
            return false;
        }
    }

}
