<%--
    Document   : gifts_edit
    Created on : 20.03.2019, 20:42:52
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
        <c:choose>
            <c:when test="${edit}">
                Geschenk bearbeiten
            </c:when>
            <c:otherwise>
                Geschenk anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard_giftit"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/gifts/list/"/>">Liste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="gift_owner">Eigentümer:</label>
                <div class="side-by-side">
                    <input type="text" name="gift_owner" value="${gift_form.values["gift_owner"][0]}" readonly="readonly">
                </div>

                <label for="gift_category">Kategorie:</label>
                <div class="side-by-side">
                    <select name="gift_category">
                        <option value="">Keine Kategorie</option>

                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${gift_form.values["gift_category"][0] == category.id.toString() ? 'selected' : ''}>
                                <c:out value="${category.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="gift_due_date">
                    Fällig am:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="gift_due_date" value="${gift_form.values["gift_due_date"][0]}">
                    <input type="text" name="gift_due_time" value="${gift_form.values["gift_due_time"][0]}">
                </div>

                <label for="gift_status">
                    Status:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="gift_status">
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}" ${gift_form.values["gift_status"][0] == status ? 'selected' : ''}>
                                <c:out value="${status.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="gift_short_text">
                    Bezeichnung:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="gift_short_text" value="${gift_form.values["gift_short_text"][0]}">
                </div>

                <label for="gift_long_text">
                    Beschreibung:
                </label>
                <div class="side-by-side">
                    <textarea name="gift_long_text"><c:out value="${gift_form.values['gift_long_text'][0]}"/></textarea>
                </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty gift_form.errors}">
                <ul class="errors">
                    <c:forEach items="${gift_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base_giftit>