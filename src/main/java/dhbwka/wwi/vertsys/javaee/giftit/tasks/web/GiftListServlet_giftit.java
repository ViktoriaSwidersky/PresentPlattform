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

import dhbwka.wwi.vertsys.javaee.giftit.tasks.ejb.CategoryBean_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.ejb.GiftBean_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.Category_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.GiftStatus;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.Gift_giftit;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die tabellarische Auflisten der Aufgaben.
 */
@WebServlet(name = "GiftListServlet", urlPatterns = {"/app/gifts/list/"})
public class GiftListServlet_giftit extends HttpServlet {

    @EJB
    private CategoryBean_giftit categoryBean;

    @EJB
    private GiftBean_giftit taskBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("statuses", GiftStatus.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchCategory = request.getParameter("search_category");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Aufgaben suchen
        Category_giftit category = null;
        GiftStatus status = null;

        if (searchCategory != null) {
            try {
                category = this.categoryBean.findById(Long.parseLong(searchCategory));
            } catch (NumberFormatException ex) {
                category = null;
            }
        }

        if (searchStatus != null) {
            try {
                status = GiftStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }

        }

        List<Gift_giftit> gifts = this.taskBean.search(searchText, category, status);
        request.setAttribute("gifts", gifts);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/gifts_giftit/gifts_list.jsp").forward(request, response);
    }
}
