/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.giftit.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.giftit.common.ejb.EntityBean_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.Category_giftit;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Kategorien.
 */
@Stateless
public class CategoryBean_giftit_soap extends EntityBean_giftit<Category_giftit, Long> {

    public CategoryBean_giftit_soap() {
        super(Category_giftit.class);
    }

    /**
     * Auslesen aller Kategorien, alphabetisch sortiert.
     *
     * @return Liste mit allen Kategorien
     */
    public List<Category_giftit> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Category_giftit c ORDER BY c.name").getResultList();
    }

}
