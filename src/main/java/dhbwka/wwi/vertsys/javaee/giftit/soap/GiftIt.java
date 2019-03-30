/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 *
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 *
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.giftit.soap;

import dhbwka.wwi.vertsys.javaee.giftit.common.ejb.UserBean_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.ejb.GiftBean_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.Category_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.GiftStatus;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.Gift_giftit;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Viktoria
 */
@Stateless
@WebService
public class GiftIt {

    @EJB
    private GiftBean_giftit giftBean;

    @EJB
    UserBean_giftit userBean;

    @WebMethod
    @WebResult(name = "status")
    public String signup(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "firstname") String firstname,
            @WebParam(name = "lastname") String lastname)
            throws UserBean_giftit.UserAlreadyExistsException {

        this.userBean.signup(username, password, firstname, lastname);
        return "OK";
    }

    @WebMethod
    @WebResult(name = "gifts")
    public List<Gift_giftit> findallgifts(
    @WebParam(name = "username") String username,
    @WebParam(name = "password") String password) throws UserBean_giftit.InvalidCredentialsException {
        
        this.userBean.validateUser(username, password);
        return giftBean.findAll();
    }

    @WebMethod
    @WebResult(name = "gifts")
    public List<Gift_giftit> search(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "search") String search,
            @WebParam(name = "category") Category_giftit category,
            @WebParam(name = "status") GiftStatus status
    ) throws UserBean_giftit.InvalidCredentialsException {
        this.userBean.validateUser(username, password);
        return giftBean.search(search, category, status);
    }

    @WebMethod
    @WebResult(name = "gifts")
    public List<Gift_giftit> findByUsername(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password) throws UserBean_giftit.InvalidCredentialsException {
         this.userBean.validateUser(username, password);
        return giftBean.findByUsername(username);
    }
}
