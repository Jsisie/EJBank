package com.ejbank.repository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class AdvisorRepository {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

}
