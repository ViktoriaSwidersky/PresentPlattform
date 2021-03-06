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
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.GiftStatus;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.Gift_giftit;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Aufgaben
 */
@Stateless
@RolesAllowed("app-user")
public class GiftBean_giftit extends EntityBean_giftit<Gift_giftit, Long> {

    @EJB
    GiftBean_giftit_soap giftBean;

    public GiftBean_giftit() {
        super(Gift_giftit.class);
    }

    /**
     * Alle Aufgaben eines Benutzers, nach Fälligkeit sortiert zurückliefern.
     *
     * @param username Benutzername
     * @return Alle Aufgaben des Benutzers
     */
    public List<Gift_giftit> findByUsername(String username) {
        return giftBean.findByUsername(username);
    }

    /**
     * Suche nach Aufgaben anhand ihrer Bezeichnung, Kategorie und Status.
     *
     * Anders als in der Vorlesung behandelt, wird die SELECT-Anfrage hier mit
     * der CriteriaBuilder-API vollkommen dynamisch erzeugt.
     *
     * @param search In der Kurzbeschreibung enthaltener Text (optional)
     * @param category Kategorie (optional)
     * @param status Status (optional)
     * @return Liste mit den gefundenen Aufgaben
     */
    public List<Gift_giftit> search(String search, Category_giftit category, GiftStatus status) {
        return giftBean.search(search, category, status);
    }
}
