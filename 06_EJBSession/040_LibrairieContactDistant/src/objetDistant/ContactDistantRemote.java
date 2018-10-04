package objetDistant;

import java.sql.SQLException;
import javax.ejb.Remote;
import metierMapping.Contact;

@Remote
public interface ContactDistantRemote
{
    public Contact lireContact(Integer numeroContact) throws SQLException;
}
