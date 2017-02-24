package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Property;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * Created by Y3895917F on 23/02/2017.
 */

@Repository
public class PropertyByCriteriaRepository {
    @PersistenceContext
    EntityManager entityManager;

    protected Session currentSession() {
        return entityManager.unwrap(Session.class);
    }

    public List<Property> filteryPropertyByCriteria(Map<String,Object> parameters){

        Criteria propertyCriteria = currentSession().createCriteria(Property.class);
        propertyCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

      Criteria localityCriteria = propertyCriteria.createCriteria("location");

        filterByPrice(parameters, propertyCriteria);


        List<Property> results = propertyCriteria.list();

        return results;

    }

    private void filterByPrice(Map<String, Object> parameters, Criteria propertyCriteria) {


        Double minPrice = (Double) parameters.get("minPrice");

            propertyCriteria.add(Restrictions.le("price", minPrice));

        }

}
