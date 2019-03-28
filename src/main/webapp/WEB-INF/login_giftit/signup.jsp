<%--
    Document   : signup
    Created on : 20.03.2019, 20:36:09
    Author     : Viktoria
--%>

<%--
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base_giftit>
    <jsp:attribute name="title">
        Registrierung
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/logout/"/>">Einloggen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <div class="form-group">
                        <label class="bolt" for="signup_forname">Vorname:<span class="required">*</span></label>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-address-book-o"></i></span>
                            </div>
                            <input type="text" class ="form-control" name="signup_forname" value="${signup_form.values["signup_forname"][0]}" placeholder="Vorname">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="bolt" for="signup_lastname">Nachname:<span class="required">*</span></label>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-address-book-o"></i></span>
                            </div>
                            <input type="text" class ="form-control" name="signup_lastname" value="${signup_form.values["signup_lastname"][0]}" placeholder="Nachname">
                        </div>
                    </div>

                    <div class="form group">
                        <label class="bolt" for="signup_username" required>Benutzername:<span class="required">*</span></label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-user"></i></span>
                            </div>
                            <input type="text" name="signup_username" class ="form-control" id="benutzername" placeholder="Benutzername eingeben" value="${signup_form.values["signup_username"][0]}">
                        </div>
                    </div>

                    <div class ="form-group">
                        <label class="bolt" for="signup_password1" required>Passwort:<span class="required">*</span></label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend ">
                                <span class="input-group-text"> <i class="fa fa-key"></i></span>
                            </div>
                            <input type="password" name="signup_password1" class = "form-control" placeholder="Passwort eingeben" value="${signup_form.values["signup_password1"][0]}">
                        </div>
                    </div>

                    <div class ="form-group">
                        <label class="bolt" for="signup_password2" required>Passwort (wdh.):<span class="required">*</span></label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend ">
                                <span class="input-group-text"> <i class="fa fa-key"></i></span>
                            </div>
                            <input type="password" name="signup_password2" class = "form-control" placeholder="Passwort wiederholen" value="${signup_form.values["signup_password2"][0]}">
                        </div>
                    </div>

                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Registrieren
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty signup_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${signup_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base_giftit>