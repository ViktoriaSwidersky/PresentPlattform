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
                            <input type="text" class ="form-control" name="forname" value="${signup_form.values["forname"][0]}" placeholder="Vorname">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="bolt" for="lastname">Nachname:</label>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-address-book-o"></i></span>
                            </div>
                            <input type="text" class ="form-control" name="lastname" value="${signup_form.values["lastname"][0]}" placeholder="Nachname">
                        </div>
                    </div>

                    <div class="form group">
                        <label class="bolt" for="username">Benutzername:</label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-user"></i></span>
                            </div>
                            <input type="text" name="username" class ="form-control" id="benutzername" value="${signup_form.values["username"][0]}" readonly="readonly">
                        </div>
                    </div>

                    <div class ="form-group">
                        <label class="bolt" for="password1">Passwort:</label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend ">
                                <span class="input-group-text"> <i class="fa fa-key"></i></span>
                            </div>
                            <input type="password" name="password1" class = "form-control" placeholder="Passwort eingeben" value="${signup_form.values["password1"][0]}">
                        </div>
                    </div>

                    <div class ="form-group">
                        <label class="bolt" for="password2">Passwort (wdh.):</label>
                        <div class = "input-group form-group">
                            <div class="input-group-prepend ">
                                <span class="input-group-text"> <i class="fa fa-key"></i></span>
                            </div>
                            <input type="password" name="password2" class = "form-control" placeholder="Passwort wiederholen" value="${signup_form.values["password2"][0]}">
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
