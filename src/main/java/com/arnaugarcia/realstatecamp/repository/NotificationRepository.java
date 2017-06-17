package com.arnaugarcia.realstatecamp.repository;

import com.arnaugarcia.realstatecamp.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Notification entity.
 */
@SuppressWarnings("unused")
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username} order by notification.date desc ")
    Page<Notification> findByUserIsCurrentUser(Pageable pageable);

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username} AND notification.seen = false order by notification.date desc")
    Page<Notification> findByUserAndSeenIsFalse(Pageable pageable);

    @Modifying
    @Query("update Notification n set n.seen = true where n = :id")
    void setReadNotification(Long id);

    @Query("update Notification n set n.seen = false where n.id = :id")
    void setUnReadNotification(Long id);

    void deleteByIdIn(List<Long> list);


}
