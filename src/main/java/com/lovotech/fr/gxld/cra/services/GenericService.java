package com.lovotech.fr.gxld.cra.services;

import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.common.SearchCriteria;
import com.lovotech.fr.gxld.core.bean.cra.common.SearchOperationEnum;
import com.lovotech.fr.gxld.core.bean.cra.domain.GenericEntity;
import com.lovotech.fr.gxld.core.bean.cra.exception.DomainErrorCodes;
import com.lovotech.fr.gxld.core.bean.cra.exception.TechnicalException;
import com.lovotech.fr.gxld.cra.repositories.GenericRepository;
import com.lovotech.fr.gxld.cra.repositories.spec.GenericSpecification;
import lombok.NonNull;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author IB
 */
public abstract class GenericService<T extends GenericEntity<T>> {

    private final GenericRepository<T> repository;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    public GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public PageDTO<T> getPage(Pageable pageable){
        return new PageDTO<T>(repository.findAll(pageable));
    }

    public T get(Long id){
        return repository.findById(id).orElseThrow(
                () -> new TechnicalException(DomainErrorCodes.OBJECT_NOT_FOUND)
        );
    }

    @Transactional
    public T update(T updated){
        T dbDomain = get(updated.getId());
        dbDomain.update(updated);

        return repository.save(dbDomain);
    }

    @Transactional
    public T create(T newDomain){
        T dbDomain = newDomain.createNewInstance();
        return repository.save(dbDomain);
    }

    @Transactional
    public void delete(Long id){
        //check if object with this id exists
        get(id);
        repository.deleteById(id);
    }
    public PageDTO<T>getAllByFilter(@NonNull Map<String,String> inputs, Pageable pageable) {
        Map<String,String> filters =  inputs.entrySet().stream().filter(i->i.getValue()!=null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if(filters.isEmpty()) return this.getPage(pageable);
        List<Specification> specs = new ArrayList<>();
        filters.forEach((key,value)->{
            if(value.length()==0){
                return;
            }
           else if(isBoolean(value)){
                    SearchCriteria searchCriteria = new SearchCriteria(key, SearchOperationEnum.EQUALITY, Boolean.valueOf(value));
                    specs.add(new GenericSpecification<T>(searchCriteria) {
                    });
            }
            else if(isDate(value)){
                SearchCriteria searchCriteria = new SearchCriteria(key, SearchOperationEnum.LIKE ,value);
                specs.add(new GenericSpecification<T>(searchCriteria) {
                });
            }
           else if(NumberUtils.isParsable(value)){
                SearchCriteria searchCriteria = new SearchCriteria(key, SearchOperationEnum.STARTS_WITH ,value);
                specs.add(new GenericSpecification<T>(searchCriteria) {
                });
            }
           else if(key.contains("_")){
                SearchCriteria searchCriteria = new SearchCriteria(key, SearchOperationEnum.JOIN, value);
                specs.add(new GenericSpecification<T>(searchCriteria));
            }
            else {
                SearchCriteria searchCriteria = new SearchCriteria(key, SearchOperationEnum.CONTAINS, value);
                specs.add(new GenericSpecification<T>(searchCriteria));
            }
        });
        if (specs.size()==0) return this.getPage(pageable);
        final Specification[] result = {specs.get(0)};
        specs.stream().skip(1).forEach(specification -> result[0] =Specification.where(result[0]).and(specification));
        return new PageDTO<T>(repository.findAll(result[0],pageable));
    }
    private boolean isDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, this.dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
    private boolean isBoolean(String val){
        val = val.toLowerCase();
        return (val.equals("true") || val.equals("false"));
    }
}
