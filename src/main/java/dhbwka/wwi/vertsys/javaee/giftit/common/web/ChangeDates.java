/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.giftit.common.web;

import dhbwka.wwi.vertsys.javaee.giftit.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.giftit.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.giftit.common.jpa.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Viktoria
 */
@WebServlet(name = "ChangeDates", urlPatterns = {"/app/gifts/changeDates/"})
public class ChangeDates extends HttpServlet {

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // aktuellen Benutzer holen
        User user = userBean.getCurrentUser();

        // Form für Userdaten erstellen
        FormValues form = new FormValues();
        Map<String, String[]> formValues = new HashMap<>();
        formValues.put("forname", new String[]{user.getForname()});
        formValues.put("lastname", new String[]{user.getLastname()});
        formValues.put("username", new String[]{user.getUsername()});
        form.setValues(formValues);

        HttpSession session = request.getSession();
        session.setAttribute("userdata", form);

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dashboard_giftit/change_dates.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        User user = userBean.getCurrentUser();
        String firstname = request.getParameter("forname");
        String lastname = request.getParameter("lastname");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String oldPw = null;

        if (request.getParameter("ak_password") != null) {
            if (user.checkPassword(request.getParameter("ak_password"))) {
                user.setForname(firstname);
                user.setLastname(lastname);
                oldPw = request.getParameter("ak_password");

                if (password1 != "" && password2 != "") {
                    if (!password1.equals(password2)) {
                        errors.add("Die eingegebenen Passwörter stimmen nicht überein!");
                    } else {
                        user.setPassword(password1);
                        validationBean.validate(user.getPassword(), errors);
                    }
                }

            } else {
                errors.add("Das aktuelle Passwort stimmt nicht!");
            }
        } else {
            errors.add("Bitte geben Sie ihr aktuelles Password ein!");
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            userBean.update(user);
            response.sendRedirect(request.getContextPath() + "/app/dashboard_giftit");
        } else {
            user.setPassword(oldPw);
            userBean.update(user);
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("userdata_errors", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }
}
