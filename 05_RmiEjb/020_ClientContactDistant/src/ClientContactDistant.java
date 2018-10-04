// ==========================================================================
// Projet RMI ContactDistant
// --------------------------------------------------------------------------
// ClientContactDistant : application cliente de l'objet distant
//                        ContactDistant
// ==========================================================================

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import metierMapping.*;
import objetDistant.ContactDistant;

public class ClientContactDistant {

    public static void main(String args[]) 
    {
        ContactDistant contactDistant;

// --------------------------------------------------------------------------
// Acces au serveur d'adressage : Il faut marquer son adresse IP
// --------------------------------------------------------------------------
        try 
        {
            String url = "//localhost:6128";

// --------------------------------------------------------------------------
// Acces a l'objet distant reference sur le serveur d'adressage par le nom
// "trieur".
// --------------------------------------------------------------------------
            contactDistant =
                (ContactDistant) Naming.lookup(url + "/contactDistant");

// --------------------------------------------------------------------------
// Utilisation de la methode trier de l'objet distant
// --------------------------------------------------------------------------
            Contact contact = contactDistant.lireContact(100);
            System.out.println(contact);
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
