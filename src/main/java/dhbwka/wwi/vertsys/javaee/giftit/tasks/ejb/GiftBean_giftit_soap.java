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
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Aufgaben
 */
@Stateless
public class GiftBean_giftit_soap extends EntityBean_giftit<Gift_giftit, Long> {

    public GiftBean_giftit_soap() {
        super(Gift_giftit.class);
    }

    /**
     * Alle Aufgaben eines Benutzers, nach Fälligkeit sortiert zurückliefern.
     *
     * @param username Benutzername
     * @return Alle Aufgaben des Benutzers
     */
    public List<Gift_giftit> findByUsername(String username) {
        return em.createQuery("SELECT t FROM Gift_giftit t WHERE t.owner.username = :username ORDER BY t.dueDate, t.dueTime")
                .setParameter("username", username)
                .getResultList();
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
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();

        // SELECT t FROM Task t
        CriteriaQuery<Gift_giftit> query = cb.createQuery(Gift_giftit.class);
        Root<Gift_giftit> from = query.from(Gift_giftit.class);
        query.select(from);

        // ORDER BY dueDate, dueTime
        query.orderBy(cb.asc(from.get("dueDate")), cb.asc(from.get("dueTime")));

        // WHERE t.shortText LIKE :search
        Predicate p = cb.conjunction();

        if (search != null && !search.trim().isEmpty()) {
            p = cb.and(p, cb.like(from.get("shortText"), "%" + search + "%"));
            query.where(p);
        }

        // WHERE t.category = :category
        if (category != null) {
            p = cb.and(p, cb.equal(from.get("category"), category));
            query.where(p);
        }

        // WHERE t.status = :status
        if (status != null) {
            p = cb.and(p, cb.equal(from.get("status"), status));
            query.where(p);
        }

        return em.createQuery(query).getResultList();
    }
}
