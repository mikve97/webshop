package services;

import models.ContactModel;
import models.OrderModel;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.sqlobject.Bind;
import persistences.ContactPersistance;
import persistences.OrderPersistence;

import java.sql.SQLException;
import java.util.List;

public class ContactService {
    private DBI dbi;
    public ContactService() throws SQLException {
        util.DbConnector.getInstance();
        dbi = util.DbConnector.getDBI();
    }

    public ContactModel searchForContactByContactNaw(ContactModel contact){
        ContactPersistance contactDAO = dbi.open(ContactPersistance.class);

        ContactModel searchContact = contactDAO.searchContact(contact.getPostalcode(), contact.getHousenumber());
        contactDAO.close();

        if(searchContact == null){
            return null;
        }else{
            return searchContact;
        }
    }

    public ContactModel createNewContactNaw(ContactModel contact){
        ContactPersistance contactDAO = dbi.open(ContactPersistance.class);
        ContactModel checkIfContactExists = this.searchForContactByContactNaw(contact);

        if(checkIfContactExists == null) {
            int newContact = contactDAO.setNewContact(contact.getName(), contact.getCompanyname(), contact.getPhonenumber(), contact.getEmail(), contact.getPostalcode(), contact.getHousenumber());
            ContactModel newContactModel = searchForContactByContactNaw(contact);
            contactDAO.close();
            return newContactModel;
        }else{
            return checkIfContactExists;
        }

    }
}
