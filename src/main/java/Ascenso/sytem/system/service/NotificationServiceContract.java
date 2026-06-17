package Ascenso.sytem.system.service;

import Ascenso.sytem.common.enums.NotificationType;
import Ascenso.sytem.system.dto.NotificationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface NotificationServiceContract {

    Page<NotificationResponseDto> getNotifications(
            Boolean read,
            NotificationType type,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    NotificationResponseDto getNotification(UUID id);

    NotificationResponseDto markAsRead(UUID id);

    void markAllAsRead();

    Long getUnreadCount();

    void createNotification
            (
                    String title,
                    String message,
                    NotificationType type
            );

}
