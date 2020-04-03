package persistences;

import mappers.ContactMapper;
import models.ContactModel;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ContactMapper.class)
public interface ContactPersistance {


    @SqlUpdate("INSERT INTO contact_naw  (name, company, phone, email, zip_code, house_number)\n" +
                "VALUES (:name, :company, :phone, :email, :zc, :hn );")
    int setNewContact(@Bind("name") String name, @Bind("company") String company, @Bind("phone") String phone, @Bind("email") String email, @Bind("zc") String zc, @Bind("hn") String hn);

    @SqlQuery("SELECT * FROM contact_naw WHERE zip_code = :zc AND house_number = :hn")
    ContactModel searchContact(@Bind("zc") String zip_code, @Bind("hn") String house_number);

    void close();
}
