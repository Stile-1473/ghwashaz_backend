package Ascenso.sytem.product.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.mapper.PageMapper;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.product.dto.CategoryResponseDto;
import Ascenso.sytem.product.dto.CreateCategoryRequestDto;
import Ascenso.sytem.product.dto.UpdateCategoryRequestDto;
import Ascenso.sytem.product.entity.Category;
import Ascenso.sytem.product.mapper.CategoryMapper;
import Ascenso.sytem.product.repository.CategoryRepository;
import Ascenso.sytem.product.service.CategoryServiceContract;
import Ascenso.sytem.product.specification.CategorySpecification;
import Ascenso.sytem.product.validator.CategoryValidator;
import jakarta.transaction.Transactional;
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
public class CategoryServiceImpl implements CategoryServiceContract {

    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

    private final CategoryValidator categoryValidator;

    private final AuditServiceContract auditService;




    @Override
    public CategoryResponseDto createCategory(CreateCategoryRequestDto request) {

        categoryValidator.validateUniqueName(request.getName());

        Category category = categoryMapper.toEntity(request);

        category.setActive(true);

        Category save = categoryRepository.save(category);

        try{

            auditService.log(
                    AuditModule.CATEGORY,
                    AuditActionType.CREATE,
                    save.getId(),
                    "Created category" + save.getName()
            );

        } catch (Exception e) {

            log.warn(
                    "Audit log failed to create category {} , error = {}",
                    save.getName(),
                    e.getMessage()
            );

        }finally {
           return categoryMapper.toResponse(save);
        }


    }

    @Override
    public CategoryResponseDto updateCategory(UUID id, UpdateCategoryRequestDto request) {

        Category category =
                categoryValidator.getValidatedCategory(id);

        if(!category.getName().equalsIgnoreCase(request.getName())){

            categoryValidator.validateUniqueName(request.getName());

        }

        Category saved = categoryRepository.save(category);

        try{
            auditService.log(
                    AuditModule.CATEGORY,
                    AuditActionType.UPDATE,
                    saved.getId(),
                    "Category updated " + saved.getName()
            );
        } catch (Exception e) {
            log.warn(
                    "Audit log failed for category update {},error={}",
                    saved.getName(),
                    e.getMessage()
            );
        }finally {
            return categoryMapper.toResponse(saved);
        }

    }

    @Override
    //@Transactional(readOnly = true)
    public CategoryResponseDto getCategory(UUID id) {

        return categoryMapper.toResponse(

                categoryValidator.getValidatedCategory(id)

        );
    }

    @Override
    public PageResponse<CategoryResponseDto> getCategories(String search, Boolean active, Pageable pageable) {

        Page<Category> page = categoryRepository.findAll(
                CategorySpecification.search(
                        search,
                        active
                ),
                pageable
        );

        return PageMapper.from(
                page.map(categoryMapper::toResponse)
        );

    }

    @Override
    public void deactivateCategory(UUID id) {

        Category category =
                categoryValidator.getValidatedCategory(id);

        category.setActive(false);

        categoryRepository.save(category);

        try {
            auditService.log(
                    AuditModule.CATEGORY,
                    AuditActionType.DISABLE,
                    category.getId(),
                    category.getName() + "category has been disabled"
            );
        }catch (Exception e){
            log.warn(
                    "Audit log failed for disabling {} category, error={}",
                    category.getName(),
                    e.getMessage()
            );
        }finally {
            log.info("{} category has been disabled",category.getName());
        }
    }

    @Override
    public void activateCategory(UUID id) {
        Category category =
                categoryValidator.getValidatedCategory(id);

        category.setActive(true);

        categoryRepository.save(category);

        try {
            auditService.log(
                    AuditModule.CATEGORY,
                    AuditActionType.DISABLE,
                    category.getId(),
                    category.getName() + "category has been enabled"
            );
        }catch (Exception e){
            log.warn(
                    "Audit log failed for enabling {} category, error={}",
                    category.getName(),
                    e.getMessage()
            );
        }finally {
            log.info("{} category has been enabled",category.getName());
        }
    }
}
