package services;

import io.dropwizard.auth.AuthenticationException;
import models.ContactModel;
import org.skife.jdbi.v2.DBI;
import persistences.ContactPersistance;

import java.sql.SQLException;
import java.util.List;

public class ContactService {
    private DBI dbi;
    private AuthenticationService authenticationService;
    public ContactService() throws SQLException {
        util.DbConnector.getInstance();
        dbi = util.DbConnector.getDBI();
        this.authenticationService = new AuthenticationService();
    }

    public ContactModel searchForContactByContactNaw(ContactModel contact){
        ContactPersistance contactDAO = dbi.open(ContactPersistance.class);

        ContactModel searchContact = contactDAO.searchContact(contact.getPostalcode(), contact.getHousenumber(), contact.getEmail());
        contactDAO.close();

        if(searchContact == null){
            return null;
        }else{
            return searchContact;
        }
    }

    public ContactModel createNewContactNawWithUserCoupling(int userid, ContactModel contact, boolean favorite){
        ContactPersistance contactDAO = dbi.open(ContactPersistance.class);
        ContactModel checkIfContactExists = this.searchForContactByContactNaw(contact);

        if(checkIfContactExists == null) {
            contactDAO.setNewContact(contact.getName(), contact.getCompanyname(), contact.getPhonenumber(), contact.getEmail(), contact.getPostalcode(), contact.getHousenumber(), favorite);
            ContactModel newContactModel = searchForContactByContactNaw(contact);
            contactDAO.close();
            this.createNewContactAccountCoupling(userid, newContactModel.getContactNawId());
            return newContactModel;
        }else{
            return checkIfContactExists;
        }
    }

    public ContactModel createNewContactNaw(ContactModel contact, boolean favorite){
        ContactPersistance contactDAO = dbi.open(ContactPersistance.class);
        ContactModel checkIfContactExists = this.searchForContactByContactNaw(contact);

        if(checkIfContactExists == null) {
            contactDAO.setNewContact(contact.getName(), contact.getCompanyname(), contact.getPhonenumber(), contact.getEmail(), contact.getPostalcode(), contact.getHousenumber(), favorite);
            ContactModel newContactModel = searchForContactByContactNaw(contact);
            contactDAO.close();
            return newContactModel;
        }else{
            return checkIfContactExists;
        }

    }

    private void createNewContactAccountCoupling(int userId, int contactNawId){
        ContactPersistance contactDAO = dbi.open(ContactPersistance.class);
        contactDAO.setContactNawCoupling(userId, contactNawId);
        contactDAO.close();
    }

    public List<ContactModel> getContactByUserId(String token, int userId) throws AuthenticationException {
        if (this.authenticationService.authenticate(token).isPresent()) {
            ContactPersistance contactDAO = dbi.open(ContactPersistance.class);
            List<ContactModel> cm = contactDAO.getContactByUserId(userId);
            contactDAO.close();
            return cm;
        }else{
            return null;
        }

    }

    public ContactModel getContactFavorite(String token, int userId) throws AuthenticationException {
        if (this.authenticationService.authenticate(token).isPresent()) {
            ContactPersistance contactDAO = dbi.open(ContactPersistance.class);
            ContactModel cm = contactDAO.getContactFavoriteByUserId(userId);
            contactDAO.close();
            return cm;
        }else{
            return null;
        }

    }

    public boolean changeFavorite(String token, int oldId, int newId) throws AuthenticationException {
        if (this.authenticationService.authenticate(token).isPresent()) {
            ContactPersistance contactDAO = dbi.open(ContactPersistance.class);
            int resultOld = contactDAO.setContactFavorite(oldId, false);
            int resultNew = contactDAO.setContactFavorite(newId, true);
            contactDAO.close();
            if(resultOld == 1 && resultNew == 1){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
