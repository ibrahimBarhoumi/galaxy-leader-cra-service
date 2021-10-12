package com.lovotech.fr.gxld.cra.repositories.spec;

import com.lovotech.fr.gxld.core.bean.cra.common.SearchCriteria;
import com.lovotech.fr.gxld.core.bean.cra.domain.GenericEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class GenericSpecification<T extends GenericEntity<T>> implements Specification<T> {
    private SearchCriteria criteria;

    public GenericSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION:
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(root.<String> get(
                        criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(root.get(
                        criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return builder.like(root.<String> get(
                        criteria.getKey()).as(String.class), criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(root.<String> get(criteria.getKey()).as(String.class), criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(root.get(
                        criteria.getKey()).as(String.class), "%" + criteria.getValue() + "%");
            case JOIN:
                String mappedBy = criteria.getKey().substring(0,criteria.getKey().indexOf("_"));
                String field = criteria.getKey().substring(criteria.getKey().indexOf("_")+1);
                return builder.like(builder.lower(root.join(mappedBy, JoinType.LEFT).get(field)),"%" + (String) criteria.getValue()+"%"
                );
            default:
                return null;
        }
    }
}

