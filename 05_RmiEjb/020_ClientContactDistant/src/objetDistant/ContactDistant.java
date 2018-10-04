package objetDistant;

// ==========================================================================
// Projet RMI ContactDistant
// --------------------------------------------------------------------------
// ContactDistant : interface objet distant
// --------------------------------------------------------------------------
// Idee : on veut donner un acces distant a la table CONTACT de la base du
//        projet mapping. Il sera possible de lire un contact dont on connait
//        le numero et d'afficher ses proprietes (toString()).
// ==========================================================================
import java.rmi.*;
import java.sql.*;
import metierMapping.*;

public interface ContactDistant extends Remote 
{

// --------------------------------------------------------------------------
// Methodes exposees
// --------------------------------------------------------------------------
    public Contact lireContact(Integer numeroContact) throws RemoteException,
                                                             SQLException;
}
