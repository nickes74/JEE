import objetDistant.MappingRMI;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;
import metierMapping.*;

public class Controleur {

    public static void main(String args[]) 
    {
        MappingRMI mappingDistant;
        Contact contact;

// --------------------------------------------------------------------------
// Acces au serveur d'adressage : Il faut marquer son adresse IP
// --------------------------------------------------------------------------
        try 
        {
            String url = "//localhost:6128";

// --------------------------------------------------------------------------
// Acces a la refrence de l'objet distant sur le serveur d'adressage
// --------------------------------------------------------------------------
            mappingDistant = 
                    (MappingRMI) Naming.lookup(url + "/mappingDistant");

// --------------------------------------------------------------------------
// Utilisation de quelques methodes de l'objet distant
// --------------------------------------------------------------------------
            Vector<Vector> listes = mappingDistant.lireListeContacts();
            System.out.println(listes.elementAt(0));

            contact = ((Vector<Contact>) listes.elementAt(0)).elementAt(0);
            String adresse = "3, rue des Coucous";

            contact.setAdresse(adresse);
            mappingDistant.modifierContact(contact);
        } 
        catch (RemoteException | 
               SQLException | 
               NotBoundException | 
               MalformedURLException e) 
        {
            System.out.println(e.getMessage());
        }
    }
}
