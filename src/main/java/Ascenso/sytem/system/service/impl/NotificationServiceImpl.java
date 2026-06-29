package Ascenso.sytem.system.service.impl;

import Ascenso.sytem.common.enums.NotificationType;
import Ascenso.sytem.system.dto.NotificationResponseDto;
import Ascenso.sytem.system.entity.Notification;
import Ascenso.sytem.system.repository.NotificationRepository;
import Ascenso.sytem.system.service.NotificationServiceContract;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationServiceContract {

    private final NotificationRepository notificationRepository;

    @Override
    public Page<NotificationResponseDto> getNotifications(
            Boolean read, NotificationType type, 
            LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return notificationRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public NotificationResponseDto getNotification(UUID id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return toDto(n);
    }

    @Override
    public NotificationResponseDto markAsRead(UUID id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        n.setIsRead(true);
        return toDto(notificationRepository.save(n));
    }

    @Override
    public void markAllAsRead() {
        notificationRepository.findAll().forEach(n -> {
            n.setIsRead(true);
            notificationRepository.save(n);
        });
    }

    @Override
    public Long getUnreadCount() {
        return notificationRepository.countByIsReadFalse();
    }

    @Override
    public void createNotification(String title, String message, NotificationType type) {
        Notification n = Notification.builder()
                .title(title).message(message).type(type).isRead(false).build();
        notificationRepository.save(n);
    }

    private NotificationResponseDto toDto(Notification n) {
        return NotificationResponseDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .message(n.getMessage())
                .type(n.getType())
                .read(n.getIsRead())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
