// ==========================================================================
// Projet RMI ContactDistant
// --------------------------------------------------------------------------
// ContactDistantServer : Serveur d'objets distants.
// ==========================================================================
// Ce programme de serveur instancie un objet ContactDistantImpl et le
// reference dans l'annuaire (lance par rmiregistry).
// ==========================================================================

import objetDistant.ContactDistantImpl;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.*;
import java.rmi.registry.LocateRegistry;

public class ContactDistantServer {

    public static void main(String args[]) throws UnknownHostException,
                                                  RemoteException,
                                                  MalformedURLException
    {
        LocateRegistry.createRegistry(6128);
    
        String adresse = "//" 
            + InetAddress.getLocalHost().getHostAddress() + ":6128";

        System.out.println("Construction de l'objet distant...");
        ContactDistantImpl contact = new ContactDistantImpl();

        System.out.println(
                "Liaison de l'objet distant sur le serveur d'annuaire...");
        Naming.rebind(adresse + "/contactDistant", contact);

        System.out.println("Attente des invocations des clients...");
    }
}
