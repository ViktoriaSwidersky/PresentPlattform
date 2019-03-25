/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.giftit.dashboard.ejb;

import java.util.ArrayList;
import java.util.List;

/**
 * Hilfsklasse zur Beschreibung einer Dashboard-Rubrik und ihrer Kacheln.
 */
public class DashboardSection_giftit {

    private String label = "";
    private List<DashboardTile_giftit> tiles = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public DashboardSection_giftit() {
    }

    public DashboardSection_giftit(String label, List<DashboardTile_giftit> tiles) {
        this.label = label;
        this.tiles = tiles;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<DashboardTile_giftit> getTiles() {
        return tiles;
    }

    public void setTiles(List<DashboardTile_giftit> tiles) {
        this.tiles = tiles;
    }
    //</editor-fold>

}
