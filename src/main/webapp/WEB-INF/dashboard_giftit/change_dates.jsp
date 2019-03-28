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
        Eigene Daten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/owndates.css"/>" />
    </jsp:attribute>
     
   <jsp:attribute name="content">  
    <form method ="post">
                    <div class="form-group col-md-6">
                        <label for="forname">
                         Vorname:
                         </label>
                        <input type="text" class="form-control" name="idea_name" value="${user_forname}" required="*">
                    </div>
                   
                    <div class="form-group col-md-6">
                        <label for="lastname">
                         Nachname:
                         </label>
                        <input type="text" class="form-control" name="price" value="${user_lastname}" required="*">
                    </div>
                    
                    <div class="form-group col-md-6">
                        <label for="username">
                         Benutzername:
                        </label>
                        <input type="text" class="form-control" name="link" value="${user_name}" required="*">
                    </div>
                    
                    <div class="form-group col-md-6">
                        <label for="Passwort">
                          Passwort:
                        </label>
                        <input type="text" class="form-control" name="password" value="${password}" required="*">
                    </div>
                    
                    <div class="form-group col-md-6">
                        <label for="Passwort2">
                          Passwort(wdh.):
                        </label>
                        <input type="text" class="form-control" name="password2" value="${password2}" required="*">
                    </div>
                        
                    <%-- Button zum Abschicken --%>
                    <div>
                        <button class="btn btn-primary" type="submit"  name="button" value="deleteIdea"><i class="fa fa-trash"></i>&nbsp;&nbsp;Änderungen speichern</button>
                    </div>  
         </form>
 </jsp:attribute>
        
</template:base_giftit>
