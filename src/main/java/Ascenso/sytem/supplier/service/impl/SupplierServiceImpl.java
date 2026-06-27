package Ascenso.sytem.supplier.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.mapper.PageMapper;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.common.utils.PhoneNumberUtils;
import Ascenso.sytem.supplier.dto.CreateSupplierRequestDto;
import Ascenso.sytem.supplier.dto.SupplierResponseDto;
import Ascenso.sytem.supplier.dto.UpdateSupplierRequestDto;
import Ascenso.sytem.supplier.entity.Supplier;
import Ascenso.sytem.supplier.mapper.SupplierMapper;
import Ascenso.sytem.supplier.repository.SupplierRepository;
import Ascenso.sytem.supplier.service.SupplierServiceContract;
import Ascenso.sytem.supplier.specification.SupplierSpecification;
import Ascenso.sytem.supplier.validator.SupplierValidator;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SupplierServiceImpl implements SupplierServiceContract {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final SupplierValidator supplierValidator;
    private final AuditServiceContract auditService;

    @Override
    public SupplierResponseDto createSupplier(CreateSupplierRequestDto dto){

        supplierValidator.validateCompanyName(dto.getCompanyName());

        String normalizedPhoneNumber = PhoneNumberUtils.normalize(dto.getPhoneNumber());
        String normalizedAlternativePhoneNumber = PhoneNumberUtils.normalize(dto.getAlternativePhoneNumber());
        supplierValidator.validatePhoneNumber(normalizedPhoneNumber);

        Supplier supplier = supplierMapper.toEntity(dto);
        supplier.setActive(true);
        supplier.setPhoneNumber(normalizedPhoneNumber);
        supplier.setAlternativePhoneNumber(normalizedAlternativePhoneNumber);

        Supplier saved = supplierRepository.save(supplier);

        try {
            auditService.log(
                    AuditModule.SUPPLIER,
                    AuditActionType.CREATE,
                    saved.getId(),
                    "Created supplier " + saved.getCompanyName()
            );
        }catch (Exception e) {
            log.warn("Audit log failed for supplier creation {},errors={}",
                    supplier.getCompanyName(),
                    e.getMessage()
            );
        }

        return supplierMapper.toResponse(saved);


    }

    @Override
    public SupplierResponseDto updateSupplier(UUID id, UpdateSupplierRequestDto dto){

        Supplier supplier = supplierValidator.getValidatedSupplier(id);

        if(!supplier.getCompanyName().equalsIgnoreCase(dto.getCompanyName())){

            supplierValidator.validateCompanyName(dto.getCompanyName());

        }

        String normalizedPhoneNumber = PhoneNumberUtils.normalize(dto.getPhoneNumber());
        String normalizedAlternativePhoneNumber = PhoneNumberUtils.normalize(dto.getAlternativePhoneNumber());

        if(!supplier.getPhoneNumber().equals(normalizedPhoneNumber)){

            supplierValidator.validatePhoneNumber(normalizedPhoneNumber);

        }

        supplier.setPhoneNumber(normalizedPhoneNumber);
        supplier.setAlternativePhoneNumber(normalizedAlternativePhoneNumber);

        supplierMapper.updateEntity(dto,supplier);

        Supplier saved=supplierRepository.save(supplier);

        try {
            auditService.log(
                    AuditModule.SUPPLIER,
                    AuditActionType.UPDATE,
                    saved.getId(),
                    "Updated supplier " + saved.getCompanyName()
            );
        } catch (Exception e){
            log.warn("Audit log failed for supplier update {},errors={}",
                    supplier.getCompanyName(),
                    e.getMessage());
        }

        return supplierMapper.toResponse(saved);

    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponseDto getSupplier(UUID id){

        return supplierMapper.toResponse(

                supplierValidator.getValidatedSupplier(id)

        );

    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SupplierResponseDto> getSuppliers(String search, Boolean active, Pageable pageable){

        Page<Supplier> page = supplierRepository.findAll(

                        SupplierSpecification.search(

                                search,

                                active

                        ),

                        pageable

                );

        return PageMapper.from(

                page.map(

                        supplierMapper::toResponse

                )

        );

    }


    @Override
    public void activateSupplier(UUID id){

        Supplier supplier =
                supplierValidator.getValidatedSupplier(id);

        supplier.setActive(true);

        supplierRepository.save(supplier);

        try{
            auditService.log(

                    AuditModule.SUPPLIER,

                    AuditActionType.ENABLE,

                    supplier.getId(),

                    "Activated supplier " + supplier.getCompanyName()

            );
        }catch (Exception e){
            log.warn(
                    "Audit log failed for activating supplier {} , error={}",
                    supplier.getCompanyName(),
                    e.getMessage()
            );
        }


    }

    @Override
    public void deactivateSupplier(UUID id){

        Supplier supplier =
                supplierValidator.getValidatedSupplier(id);

        supplier.setActive(false);

        supplierRepository.save(supplier);




        try{
            auditService.log(

                    AuditModule.SUPPLIER,

                    AuditActionType.DISABLE,

                    supplier.getId(),

                    "Deactivated supplier " + supplier.getCompanyName()

            );
        }catch (Exception e){
            log.warn(
                    "Audit log failed for deactivating supplier {} , error={}",
                    supplier.getCompanyName(),
                    e.getMessage()
            );
        }

    }


}