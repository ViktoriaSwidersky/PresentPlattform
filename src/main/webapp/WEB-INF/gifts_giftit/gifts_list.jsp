<%--
    Document   : gifts_list
    Created on : 20.03.2019, 20:43:12
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
        Liste der Geschenke
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard_giftit"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/gifts/gift/new"/>">Geschenk anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/gifts/categories/"/>">Kategorien bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>

            <select name="search_category">
                <option value="">Alle Kategorien</option>

                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}" ${param.search_category == category.id ? 'selected' : ''}>
                        <c:out value="${category.name}" />
                    </option>
                </c:forEach>
            </select>

            <select name="search_status">
                <option value="">Alle Stati</option>

                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${param.search_status == status ? 'selected' : ''}>
                        <c:out value="${status.label}"/>
                    </option>
                </c:forEach>
            </select>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty gifts}">
                <p>
                    Es wurden keine Geschenke gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.giftit.common.web.WebUtils"/>

                <table>
                    <thead>
                        <tr>
                            <th>Bezeichnung</th>
                            <th>Kategorie</th>
                            <th>Eigentümer</th>
                            <th>Status</th>
                            <th>Fällig am</th>
                        </tr>
                    </thead>
                    <c:forEach items="${gifts}" var="gift">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/gifts/gift/${gift.id}/"/>">
                                    <c:out value="${gift.shortText}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${gift.category.name}"/>
                            </td>
                            <td>
                                <c:out value="${gift.owner.username}"/>
                            </td>
                            <td>
                                <c:out value="${gift.status.label}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(gift.dueDate)}"/>
                                <c:out value="${utils.formatTime(gift.dueTime)}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base_giftit>
