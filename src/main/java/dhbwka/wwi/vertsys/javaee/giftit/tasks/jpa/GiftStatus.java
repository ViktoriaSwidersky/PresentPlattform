/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa;

/**
 * Statuswerte einer Aufgabe.
 */
public enum GiftStatus {
    IDEA, IN_ORDER, DELIVERD, CANCELED;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case IDEA:
                return "Idee";
            case IN_ORDER:
                return "Bestellt";
            case CANCELED:
                return "Verworfen";
            case DELIVERD:
                return "Ausgeliefert";
            default:
                return this.toString();
        }
    }

}
