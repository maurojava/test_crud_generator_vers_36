/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mauro.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mauro.entity.PurchaseOrder;

/**
 *
 * @author utente_javaee7
 */
@Stateless
public class PurchaseOrderFacade extends AbstractFacade<PurchaseOrder> {

    @PersistenceContext(unitName = "test_crud_generator_vers_36PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PurchaseOrderFacade() {
        super(PurchaseOrder.class);
    }
    
}
