<%--
    Document   : login
    Created on : 20.03.2019, 20:36:50
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

<template:base_giftit>
    <jsp:attribute name="title">
        Login
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/SignUp"/>">Registrieren</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form action="j_security_check" method="post" class="stacked">
                <div class="column">
                    <%-- Eingabefelder --%>
                    <div class="form group">
                        <label class="bolt" for="j_username" required>Benutzername:<span class="required">*</span></label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-user"></i></span>
                            </div>
                            <input type="text" name="j_username" class ="form-control" id="benutzername" placeholder="Benutzername eingeben">
                        </div>
                    </div>

                    <div class ="form-group">
                        <label class="bolt" for="j_password" required>Passwort:<span class="required">*</span></label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend ">
                                <span class="input-group-text"> <i class="fa fa-key"></i></span>
                            </div>
                            <input type="password" name="j_password" class = "form-control" placeholder="Passwort eingeben">
                        </div>
                    </div>

                    <%-- Button zum Abschicken --%>
                    <button class="icon-login" type="submit">
                        Einloggen
                    </button>
                </div>
            </form>
        </div>
    </jsp:attribute>
</template:base_giftit>