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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Viktoria
 */
@Stateless
public class GiftIt {

    @EJB
    private GiftBean_giftit giftBean;

    @WebMethod
    @WebResult(name = "gifts")
    public List<Gift_giftit> findallgifts() {
        return giftBean.findAll();
    }

    @WebMethod
    @WebResult(name = "gifts")
    public List<Gift_giftit> search(
            @WebParam(name = "search") String search,
            @WebParam(name = "category") Category_giftit category,
            @WebParam(name = "status") GiftStatus status
    ) {
        return giftBean.search(search, category, status);
    }

    @WebMethod
    @WebResult(name = "gifts")
    public List<Gift_giftit> findByUsername(@WebParam(name = "username") String username) {
        return giftBean.findByUsername(username);
    }
}
