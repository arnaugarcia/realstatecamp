package com.arnaugarcia.realstatecamp.web.rest.util;

import com.arnaugarcia.realstatecamp.domain.Notification;
import com.arnaugarcia.realstatecamp.domain.Property;
import com.arnaugarcia.realstatecamp.domain.User;

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
