package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Notification entity.
 */
@SuppressWarnings("unused")
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username} order by notification.date desc ")
    List<Notification> findByUserIsCurrentUser();

}
