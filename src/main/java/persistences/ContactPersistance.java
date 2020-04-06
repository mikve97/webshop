package persistences;

import mappers.ContactMapper;
import models.ContactModel;
import models.ProductModel;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(ContactMapper.class)
public interface ContactPersistance {


    @SqlUpdate("INSERT INTO contact_naw  (name, company, phone, email, zip_code, house_number, favorite)\n" +
                "VALUES (:name, :company, :phone, :email, :zc, :hn, :favorite );")
    int setNewContact(@Bind("name") String name, @Bind("company") String company, @Bind("phone") String phone, @Bind("email") String email, @Bind("zc") String zc, @Bind("hn") String hn, @Bind("favorite") boolean favorite);

    @SqlUpdate("INSERT INTO account_contact_coupling  (account_id, contact_naw_id)\n" +
            "VALUES (:userId, :contactId);")
    int setContactNawCoupling(@Bind("userId") int userId, @Bind("contactId") int contactId);

    @SqlUpdate("UPDATE contact_naw  \n" +
            "SET favorite = :favorite \n" +
            "WHERE contact_naw_id = :contactId;  ")
    int setContactFavorite(@Bind("contactId") int contactId, @Bind("favorite") boolean favorite);

    @SqlQuery("SELECT * FROM contact_naw WHERE zip_code = :zc AND house_number = :hn")
    ContactModel searchContact(@Bind("zc") String zip_code, @Bind("hn") String house_number);

    @SqlQuery("SELECT cn.* FROM contact_naw cn LEFT JOIN account_contact_coupling acc ON acc.contact_naw_id = cn.contact_naw_id LEFT JOIN account a ON acc.account_id = a.user_id WHERE a.user_id = :userId")
    List<ContactModel> getContactByUserId(@Bind("userId") int userId);

    @SqlQuery("SELECT cn.* FROM contact_naw cn LEFT JOIN account_contact_coupling acc ON acc.contact_naw_id = cn.contact_naw_id LEFT JOIN account a ON acc.account_id = a.user_id WHERE a.user_id = :userId AND cn.favorite = true")
    ContactModel getContactFavoriteByUserId(@Bind("userId") int userId);

    void close();
}
