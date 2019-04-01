/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.giftit.tasks.web;

import dhbwka.wwi.vertsys.javaee.giftit.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.giftit.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.ejb.GiftBean_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.common.ejb.UserBean_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.common.ejb.ValidationBean;

import dhbwka.wwi.vertsys.javaee.giftit.tasks.ejb.CategoryBean_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.GiftStatus;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.Gift_giftit;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(name = "GiftEditServlet", urlPatterns = {"/app/gifts/gift/*"})
public class GiftEditServlet_giftit extends HttpServlet {

    @EJB
    GiftBean_giftit taskBean;

    @EJB
    CategoryBean_giftit categoryBean;

    @EJB
    UserBean_giftit userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("statuses", GiftStatus.values());

        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Gift_giftit gift = this.getRequestedGift(request);
        request.setAttribute("edit", gift.getId() != 0);

        if (session.getAttribute("gift_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("gift_form", this.createGiftForm(gift));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/gifts_giftit/gifts_edit.jsp").forward(request, response);

        session.removeAttribute("gift_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveGift(request, response);
                break;
            case "delete":
                this.deleteGift(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveGift(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String giftCategory = request.getParameter("gift_category");
        String giftDueDate = request.getParameter("gift_due_date");
        String giftDueTime = request.getParameter("gift_due_time");
        String giftStatus = request.getParameter("gift_status");
        String giftShortText = request.getParameter("gift_short_text");
        String giftLongText = request.getParameter("gift_long_text");

        Gift_giftit gift = this.getRequestedGift(request);

        if (giftCategory != null && !giftCategory.trim().isEmpty()) {
            try {
                gift.setCategory(this.categoryBean.findById(Long.parseLong(giftCategory)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date dueDate = WebUtils.parseDate(giftDueDate);
        Time dueTime = WebUtils.parseTime(giftDueTime);

        if (dueDate != null) {
            gift.setDueDate(dueDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (dueTime != null) {
            gift.setDueTime(dueTime);
        } else {
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }

        try {
            gift.setStatus(GiftStatus.valueOf(giftStatus));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }

        gift.setShortText(giftShortText);
        gift.setLongText(giftLongText);

        this.validationBean.validate(gift, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.taskBean.update(gift);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard_giftit"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("gift_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteGift(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Gift_giftit gift = this.getRequestedGift(request);
        this.taskBean.delete(gift);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/GiftList"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Gift_giftit getRequestedGift(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Gift_giftit gift = new Gift_giftit();
        gift.setOwner(this.userBean.getCurrentUser());
        gift.setDueDate(new Date(System.currentTimeMillis()));
        gift.setDueTime(new Time(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String giftId = request.getPathInfo();

        if (giftId == null) {
            giftId = "";
        }

        giftId = giftId.substring(1);

        if (giftId.endsWith("/")) {
            giftId = giftId.substring(0, giftId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            gift = this.taskBean.findById(Long.parseLong(giftId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return gift;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param task Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createGiftForm(Gift_giftit gift) {
        Map<String, String[]> values = new HashMap<>();

        values.put("gift_owner", new String[]{
            gift.getOwner().getUsername()
        });

        if (gift.getCategory() != null) {
            values.put("gift_category", new String[]{
                "" + gift.getCategory().getId()
            });
        }

        values.put("gift_due_date", new String[]{
            WebUtils.formatDate(gift.getDueDate())
        });

        values.put("gift_due_time", new String[]{
            WebUtils.formatTime(gift.getDueTime())
        });

        values.put("gift_status", new String[]{
            gift.getStatus().toString()
        });

        values.put("gift_short_text", new String[]{
            gift.getShortText()
        });

        values.put("gift_long_text", new String[]{
            gift.getLongText()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
