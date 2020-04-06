package mappers;

import models.ContactModel;
import models.UserModel;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper  implements ResultSetMapper<ContactModel> {
    @Override
    public ContactModel map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {
        ContactModel contact  =  new ContactModel(
                rs.getString("zip_code"),
                rs.getString("house_number"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("company"),
                rs.getBoolean("favorite")
        );

        contact.setContactNawId(rs.getInt("contact_naw_id"));

        return contact;
    }
}
