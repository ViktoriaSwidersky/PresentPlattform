/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.giftit.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.giftit.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.giftit.dashboard.ejb.DashboardContentProvider_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.dashboard.ejb.DashboardSection_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.dashboard.ejb.DashboardTile_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.Category_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.GiftStatus;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * EJB zur Definition der Dashboard-Kacheln für Aufgaben.
 */
@Stateless(name = "tasks")
public class DashboardContent_giftit implements DashboardContentProvider_giftit {

    @EJB
    private CategoryBean_giftit categoryBean;

    @EJB
    private GiftBean_giftit giftBean;

    /**
     * Vom Dashboard aufgerufenen Methode, um die anzuzeigenden Rubriken und
     * Kacheln zu ermitteln.
     *
     * @param sections Liste der Dashboard-Rubriken, an die die neuen Rubriken
     * angehängt werden müssen
     */
    @Override
    public void createDashboardContent(List<DashboardSection_giftit> sections) {
        // Zunächst einen Abschnitt mit einer Gesamtübersicht aller Aufgaben
        // in allen Kategorien erzeugen
        DashboardSection_giftit section = this.createSection(null);
        sections.add(section);

        // Anschließend je Kategorie einen weiteren Abschnitt erzeugen
        List<Category_giftit> categories = this.categoryBean.findAllSorted();

        for (Category_giftit category : categories) {
            section = this.createSection(category);
            sections.add(section);
        }
    }

    /**
     * Hilfsmethode, die für die übergebene Aufgaben-Kategorie eine neue Rubrik
     * mit Kacheln im Dashboard erzeugt. Je Aufgabenstatus wird eine Kachel
     * erzeugt. Zusätzlich eine Kachel für alle Aufgaben innerhalb der
     * jeweiligen Kategorie.
     *
     * Ist die Kategorie null, bedeutet dass, dass eine Rubrik für alle Aufgaben
     * aus allen Kategorien erzeugt werden soll.
     *
     * @param category Aufgaben-Kategorie, für die Kacheln erzeugt werden sollen
     * @return Neue Dashboard-Rubrik mit den Kacheln
     */
    private DashboardSection_giftit createSection(Category_giftit category) {
        // Neue Rubrik im Dashboard erzeugen
        DashboardSection_giftit section = new DashboardSection_giftit();
        String cssClass = "";

        if (category != null) {
            section.setLabel(category.getName());
        } else {
            section.setLabel("Alle Kategorien");
            cssClass = "overview";
        }

        // Eine Kachel für alle Aufgaben in dieser Rubrik erzeugen
        DashboardTile_giftit tile = this.createTile(category, null, "Alle", cssClass + " status-all", "calendar");
        section.getTiles().add(tile);

        // Ja Aufgabenstatus eine weitere Kachel erzeugen
        for (GiftStatus status : GiftStatus.values()) {
            String cssClass1 = cssClass + " status-" + status.toString().toLowerCase();
            String icon = "";

            switch (status) {
                case IDEA:
                    icon = "idea";
                    break;
                case IN_ORDER:
                    icon = "package";
                    break;
                case CANCELED:
                    icon = "cancel";
                    break;
                case DELIVERD:
                    icon = "tick";
                    break;
            }

            tile = this.createTile(category, status, status.getLabel(), cssClass1, icon);
            section.getTiles().add(tile);
        }

        // Erzeugte Dashboard-Rubrik mit den Kacheln zurückliefern
        return section;
    }

    /**
     * Hilfsmethode zum Erzeugen einer einzelnen Dashboard-Kachel. In dieser
     * Methode werden auch die in der Kachel angezeigte Anzahl sowie der Link,
     * auf den die Kachel zeigt, ermittelt.
     *
     * @param category
     * @param status
     * @param label
     * @param cssClass
     * @param icon
     * @return
     */
    private DashboardTile_giftit createTile(Category_giftit category, GiftStatus status, String label, String cssClass, String icon) {
        int amount = giftBean.search(null, category, status).size();
        String href = "/app/gifts/list/";

        if (category != null) {
            href = WebUtils.addQueryParameter(href, "search_category", "" + category.getId());
        }

        if (status != null) {
            href = WebUtils.addQueryParameter(href, "search_status", status.toString());
        }

        DashboardTile_giftit tile = new DashboardTile_giftit();
        tile.setLabel(label);
        tile.setCssClass(cssClass);
        tile.setHref(href);
        tile.setIcon(icon);
        tile.setAmount(amount);
        tile.setShowDecimals(false);
        return tile;
    }

}
