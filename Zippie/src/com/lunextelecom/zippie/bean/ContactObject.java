package com.lunextelecom.zippie.bean;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactObject.
 */
public class ContactObject {
    public int cId;
    public int getcId() {
        return cId;
    }
    public void setcId(int cId) {
        this.cId = cId;
    }

    /** The c name. */
    public String cName;

    /** The c avartar. */
    public String cAvartar;

    /** The vippe free id. */
    public String vippeFreeId;

    /** The vippe paid id. */
    public String vippePaidId;

    /** The c list number. */
    public ArrayList<String> cListNumber;

    /** The c status. */
    public Boolean cStatus;

    /** The c favorites. */
    public String cFavorites;

    /**
     * Instantiates a new contact object.
     */
    public ContactObject() {
        // TODO Auto-generated constructor stub
    }
    /**
     * Instantiates a new contact object.
     *
     * @param contact the contact
     */
    public ContactObject(ContactObject contact){
        this.cName = contact.getcName();
        this.cAvartar = contact.getcAvartar();
        this.vippeFreeId = contact.getVippeFreeId();
        this.vippePaidId = contact.getVippePaidId();
        this.cListNumber = contact.getcListNumber();
        this.cFavorites = contact.getcFavorites();
        this.cId = contact.getcId();
    }

    /**
     * Gets the c name.
     *
     * @return the c name
     */
    public String getcName() {
        return cName;
    }

    /**
     * Sets the c name.
     *
     * @param cName the new c name
     */
    public void setcName(String cName) {
        this.cName = cName;
    }

    /**
     * Gets the c avartar.
     *
     * @return the c avartar
     */
    public String getcAvartar() {
        return cAvartar;
    }

    /**
     * Sets the c avartar.
     *
     * @param cAvartar the new c avartar
     */
    public void setcAvartar(String cAvartar) {
        this.cAvartar = cAvartar;
    }

    /**
     * Gets the vippe free id.
     *
     * @return the vippe free id
     */
    public String getVippeFreeId() {
        return vippeFreeId;
    }

    /**
     * Sets the vippe free id.
     *
     * @param vippeFreeId the new vippe free id
     */
    public void setVippeFreeId(String vippeFreeId) {
        this.vippeFreeId = vippeFreeId;
    }

    /**
     * Gets the vippe paid id.
     *
     * @return the vippe paid id
     */
    public String getVippePaidId() {
        return vippePaidId;
    }

    /**
     * Sets the vippe paid id.
     *
     * @param vippePaidId the new vippe paid id
     */
    public void setVippePaidId(String vippePaidId) {
        this.vippePaidId = vippePaidId;
    }

    /**
     * Gets the c list number.
     *
     * @return the c list number
     */
    public ArrayList<String> getcListNumber() {
        return cListNumber;
    }

    /**
     * Sets the c list number.
     *
     * @param cListNumber the new c list number
     */
    public void setcListNumber(ArrayList<String> cListNumber) {
        this.cListNumber = cListNumber;
    }

    /**
     * Gets the c status.
     *
     * @return the c status
     */
    public Boolean getcStatus() {
        return cStatus;
    }

    /**
     * Sets the c status.
     *
     * @param cStatus the new c status
     */
    public void setcStatus(Boolean cStatus) {
        this.cStatus = cStatus;
    }

    public String getcFavorites() {
        return cFavorites;
    }

    /**
     * Sets the c favorites.
     *
     * @param cFavorites the new c favorites
     */
    public void setcFavorites(String cFavorites) {
        this.cFavorites = cFavorites;
    }

}
