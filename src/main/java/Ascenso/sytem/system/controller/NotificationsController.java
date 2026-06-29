package Ascenso.sytem.system.controller;

import Ascenso.sytem.common.constants.Permissions;
import Ascenso.sytem.common.enums.NotificationType;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.system.dto.NotificationCountResponseDto;
import Ascenso.sytem.system.dto.NotificationResponseDto;
import Ascenso.sytem.system.service.NotificationServiceContract;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationsController {

    private final NotificationServiceContract notificationService;

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).REPORT_VIEW)")
    public ApiResponse<Page<NotificationResponseDto>> getNotifications(
            @RequestParam(required = false) Boolean read,
            @RequestParam(required = false) NotificationType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<NotificationResponseDto> notifications = notificationService.getNotifications(
                read, type, startDate, endDate, pageable);
        
        return ApiResponse.<Page<NotificationResponseDto>>builder()
                .success(true)
                .data(notifications)
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).REPORT_VIEW)")
    public ApiResponse<NotificationResponseDto> getNotification(@PathVariable UUID id) {
        NotificationResponseDto notification = notificationService.getNotification(id);
        return ApiResponse.<NotificationResponseDto>builder()
                .success(true)
                .data(notification)
                .build();
    }

    @PostMapping("/{id}/read")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).EXPENSE_VIEW)")
    public ApiResponse<NotificationResponseDto> markAsRead(@PathVariable UUID id) {
        NotificationResponseDto notification = notificationService.markAsRead(id);
        return ApiResponse.<NotificationResponseDto>builder()
                .success(true)
                .message("Marked as read")
                .data(notification)
                .build();
    }

    @PostMapping("/read-all")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).EXPENSE_VIEW)")
    public ApiResponse<Void> markAllAsRead() {
        notificationService.markAllAsRead();
        return ApiResponse.<Void>builder()
                .success(true)
                .message("All marked as read")
                .build();
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).REPORT_VIEW)")
    public ApiResponse<NotificationCountResponseDto> getUnreadCount() {
        Long count = notificationService.getUnreadCount();
        return ApiResponse.<NotificationCountResponseDto>builder()
                .success(true)
                .data(NotificationCountResponseDto.builder().count(count).build())
                .build();
    }
}
