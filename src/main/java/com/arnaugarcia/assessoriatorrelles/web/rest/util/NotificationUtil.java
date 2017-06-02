package com.arnaugarcia.assessoriatorrelles.web.rest.util;

import com.arnaugarcia.assessoriatorrelles.domain.Notification;
import com.arnaugarcia.assessoriatorrelles.domain.Property;
import com.arnaugarcia.assessoriatorrelles.domain.User;

import java.time.ZonedDateTime;

/**
 * Created by arnau on 8/5/17.
 */
public final class NotificationUtil {

    public static Notification createSuccessNotification(Property property, User user){
        Notification successNotification = new Notification();
        //TODO Translate this
        successNotification.setTitle("Has creado la porpiedad " + property.getName());
        successNotification.setContent("Mira cómo queda en la web!");
        successNotification.setDate(ZonedDateTime.now());
        successNotification.setSeen(false);
        successNotification.setUser(user);
       return successNotification;
    }
}
