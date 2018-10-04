package objetDistant;

import java.rmi.*;
import java.sql.SQLException;
import java.util.Vector;

import metierMapping.Contact;

public interface MappingRMI extends Remote
{
    
// ----------------------------------------------------------------------------
// Chercher un contact (lire) : retourne le contact "numero" ou une Exception
// ----------------------------------------------------------------------------
    public Contact lireContact(Integer numero) throws RemoteException,
                                                      SQLException;

// ----------------------------------------------------------------------------
// Liste de tous les contacts ou Exception
// ----------------------------------------------------------------------------
// Le Vector<Vector> retourne contient deux Vector :
// . Le premier (poste 0) contient le Vector<Contact> de tous les contacts
// . Le deuxieme (poste 1) contient le Vector<Colonne> des colonnes de la table
//   des contacts.
// ----------------------------------------------------------------------------
    public Vector<Vector> lireListeContacts() throws RemoteException,
                                                     SQLException;

// ----------------------------------------------------------------------------
// Modification d'un contact.
// ----------------------------------------------------------------------------
// Retourne 1 si la modification est reussie, 0 si incident (le contact
// n'existe pas) ou une Exception
// ----------------------------------------------------------------------------
    public int modifierContact(Contact contact) throws RemoteException,
                                                       SQLException;

// ----------------------------------------------------------------------------
// Liste de tous les secteurs ou Exception
// ----------------------------------------------------------------------------
// Le Vector<Vector> retourne contient deux Vector :
// . Le premier (poste 0) contient le Vector<Secteur> de tous les secteurs
// . Le deuxieme (poste 1) contient le Vector<Colonne> des colonnes de la table
//   des secteurs.
// ----------------------------------------------------------------------------
    public Vector<Vector> lireListeSecteurs() throws RemoteException,
                                                     SQLException;
}
