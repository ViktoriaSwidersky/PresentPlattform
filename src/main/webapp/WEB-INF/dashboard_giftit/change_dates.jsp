<%--
    Document   : change_dates
    Created on : 28.03.2019, 15:22:13
    Author     : Viktoria
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base_giftit>
    <jsp:attribute name="title">
        Benutzerdaten ändern
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/changesdates.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard_giftit"/>">Dashboard</a>
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
                        <label class="bolt" for="forname">Vorname:</label>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-address-book-o"></i></span>
                            </div>
                            <input type="text" class ="form-control" name="forname" value="${userdata.values["forname"][0]}" placeholder="Vorname">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="bolt" for="lastname">Nachname:</label>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-address-book-o"></i></span>
                            </div>
                            <input type="text" class ="form-control" name="lastname" value="${userdata.values["lastname"][0]}" placeholder="Nachname">
                        </div>
                    </div>

                    <div class="form group">
                        <label class="bolt" for="username">Benutzername:</label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-user"></i></span>
                            </div>
                            <input type="text" name="username" class ="form-control" id="benutzername" value="${userdata.values["username"][0]}" readonly="readonly">
                        </div>
                    </div>

                    <div class ="form-group">
                        <label class="bolt" for="ak_password">aktuelles Passwort:</label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend ">
                                <span class="input-group-text"> <i class="fa fa-key"></i></span>
                            </div>
                            <input type="password" name="ak_password" class = "form-control" placeholder="Passwort eingeben" value="${userdata.values["ak_password"][0]}">
                        </div>
                    </div>

                    <div class ="form-group">
                        <label class="bolt" for="password1">Passwort:</label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend ">
                                <span class="input-group-text"> <i class="fa fa-key"></i></span>
                            </div>
                            <input type="password" name="password1" class = "form-control" placeholder="Passwort eingeben" value="${userdata.values["password1"][0]}">
                        </div>
                    </div>

                    <div class ="form-group">
                        <label class="bolt" for="password2">Passwort (wdh.):</label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend ">
                                <span class="input-group-text"> <i class="fa fa-key"></i></span>
                            </div>
                            <input type="password" name="password2" class = "form-control" placeholder="Passwort wiederholen" value="${userdata.values["password2"][0]}">
                        </div>
                    </div>

                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Änderung speichern
                            <i class="fa fa-check" aria-hidden="true"></i></button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty userdata_errors.errors}">
                    <ul class="errors">
                        <c:forEach items="${userdata_errors.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base_giftit>
