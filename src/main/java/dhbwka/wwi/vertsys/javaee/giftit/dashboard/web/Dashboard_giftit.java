/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
/**
 *
 * @author Viktoria
 */
/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.giftit.dashboard.web;

import dhbwka.wwi.vertsys.javaee.giftit.dashboard.ejb.DashboardContentProvider_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.dashboard.ejb.DashboardSection_giftit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die Startseite mit dem Übersichts-Dashboard.
 */
@WebServlet(name = "dashboard_giftit", urlPatterns = {"/app/dashboard_giftit"})
public class Dashboard_giftit extends HttpServlet {

    // Kacheln für Aufgaben
    @EJB
    DashboardContentProvider_giftit giftContent;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Dashboard-Rubriken und Kacheln erzeugen und im Request Context ablegen
        List<DashboardSection_giftit> sections = new ArrayList<>();
        request.setAttribute("sections", sections);

        giftContent.createDashboardContent(sections);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/dashboard_giftit/dashboard.jsp").forward(request, response);
    }
}
