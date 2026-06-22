package Ascenso.sytem.audit.controller;

import Ascenso.sytem.audit.dto.AuditLogsResponseDto;
import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/audit")
public class AuditController {

    private final AuditServiceContract auditServiceContract;

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).AUDIT_VIEW)")

    public ResponseEntity<ApiResponse<PageResponse<AuditLogsResponseDto>>> getAuditLogs(
            @RequestParam(required = false)
            AuditModule module,

            @RequestParam(required = false)
            AuditActionType actionType,

            @RequestParam(required = false)
            String search,

            @PageableDefault(
                    page =0,
                    size = 20,
                    sort = "createdAt"
            )
            Pageable pageable

    ){

        return ResponseEntity.ok(
                ApiResponse.<PageResponse<AuditLogsResponseDto>>builder()
                        .success(true)
                        .message("Audit logs retrieved successfully")
                        .data(
                                auditServiceContract.getLogs(
                                        module,
                                        actionType,
                                        search,
                                        pageable
                                )
                        )

                        .build()
                );

    }

}
