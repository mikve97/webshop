package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactModel {
    private int contactNawId;
    private String postalcode;
    private String housenumber;
    private String name;
    private String email;
    private String phonenumber;
    private String companyname;
    private boolean favorite;



    public ContactModel(String postalcode, String housenumber, String name, String email,
                        String phonenumber, String companyname, boolean favorite) {
        this.postalcode = postalcode;
        this.housenumber = housenumber;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.companyname = companyname;
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public void setContactNawId(int cw){
        this.contactNawId = cw;
    }

    public int getContactNawId(){
        return this.contactNawId;
    }



}


