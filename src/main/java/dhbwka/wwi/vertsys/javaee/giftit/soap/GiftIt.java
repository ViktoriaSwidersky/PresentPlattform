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
import dhbwka.wwi.vertsys.javaee.giftit.tasks.ejb.CategoryBean_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.ejb.CategoryBean_giftit_soap;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.ejb.GiftBean_giftit_soap;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.Category_giftit;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.GiftStatus;
import dhbwka.wwi.vertsys.javaee.giftit.tasks.jpa.Gift_giftit;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author Viktoria
 */
@Stateless
@WebService
public class GiftIt {

    @EJB
    private GiftBean_giftit_soap giftBean;

    @EJB
    UserBean_giftit userBean;

    @EJB
    CategoryBean_giftit_soap categoryBean;

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
            @WebParam(name = "category") String category,
            @WebParam(name = "status") GiftStatus status
    ) throws UserBean_giftit.InvalidCredentialsException {
        this.userBean.validateUser(username, password);
        List<Category_giftit> sortedList = categoryBean.findAllSorted();
        Category_giftit chooseCategory = null;
        for (Category_giftit cg : sortedList) {
            if (cg.getName().equals(category)) {
                chooseCategory = cg;
            }
        }
        return giftBean.search(search, chooseCategory, status);
    }

    @WebMethod
    @WebResult(name = "gifts")
    public List<Gift_giftit> findByUsername(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "username_search") String username_search) throws UserBean_giftit.InvalidCredentialsException {
        this.userBean.validateUser(username, password);
        return giftBean.findByUsername(username_search);
    }
}
