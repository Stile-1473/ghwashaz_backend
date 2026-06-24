package Ascenso.sytem.audit.service.impl;

import Ascenso.sytem.audit.dto.AuditLogsResponseDto;
import Ascenso.sytem.audit.entity.AuditLogs;
import Ascenso.sytem.audit.mapper.AuditMapper;
import Ascenso.sytem.audit.repo.AuditRepository;
import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.audit.specification.AuditSpecification;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.common.mapper.PageMapper;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.security.util.SecurityUtils;
import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuditServiceImpl implements AuditServiceContract {

   private final AuditRepository auditRepository;

   private final AuditMapper auditMapper;

   private final UserRepository userRepository;

    @Override
    public void log(AuditModule module, AuditActionType action, UUID entityId, String description) {

        User user = getCurrentUser();

        AuditLogs auditLog = AuditLogs.builder()
                .phoneNumber(user.getPhoneNumber())
                .fullName(user.getFullName())
                .module(module)
                .action(action)
                .entityId(entityId)
                .description(description)
                .ipAddress(getClientIp())
                .device(getDevice())
                .build();


        auditRepository.save(auditLog);

    }

    @Override
    public PageResponse<AuditLogsResponseDto> getLogs(AuditModule module, AuditActionType action, String search, Pageable pageable) {
        Page<AuditLogs> page = auditRepository.findAll(
                AuditSpecification.filter(
                        module,
                        action,
                        search
                ),
                pageable
        );

        return PageMapper.from(
                page.map(auditMapper::toResponse)
        );
    }


    private User getCurrentUser(){
        UUID id = SecurityUtils.getCurrentUserId();

        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(
                "Authenticated user not found"
        ));
    }

    private String getClientIp(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null){
            return "UNKNOWN";
        }

        HttpServletRequest request = attributes.getRequest();

        String forwarded = request.getHeader("X-Forwarded-For");

        if(forwarded != null && !forwarded.isBlank()){
            return forwarded.split(",")[0];

        }

        return request.getRemoteAddr();
    }


    private String getDevice(){
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if(attributes == null){
            return "UNKNOWN";
        }

        return  attributes.getRequest().getHeader("User-Agent");
    }
}
