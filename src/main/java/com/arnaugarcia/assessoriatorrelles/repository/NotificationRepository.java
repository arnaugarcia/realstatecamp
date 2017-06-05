package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the Notification entity.
 */
@SuppressWarnings("unused")
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username} order by notification.date desc ")
    Page<Notification> findByUserIsCurrentUser(Pageable pageable);

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username} AND notification.seen = false order by notification.date desc")
    Page<Notification> findByUserAndSeenIsFalse(Pageable pageable);

    @Query("update Notification n set n.seen = true where n = :notification")
    void setReadNotification(Notification notification);

    @Query("update Notification n set n.seen = false where n.id = :id")
    void setUnReadNotification(Long id);

}
