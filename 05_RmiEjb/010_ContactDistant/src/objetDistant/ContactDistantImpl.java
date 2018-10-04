package objetDistant;

// ==========================================================================
// Projet RMI ContactDistant
// --------------------------------------------------------------------------
// ContactDistantImpl : implementation de l'objet distant de type
//                      ContactDistant
// ==========================================================================
import java.rmi.*;
import java.rmi.server.*;
import utilitairesMG.jdbc.*;
import metierMapping.*;
import daoJdbcMapping.*;
import java.sql.*;

public class ContactDistantImpl extends UnicastRemoteObject
                                implements ContactDistant
{

    private BaseDeDonnees base;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public ContactDistantImpl() throws RemoteException {

// --------------------------------------------------------------------------
// Chargement du driver SQL
// --------------------------------------------------------------------------
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Driver inconnu : " + e.getMessage());
        }

// --------------------------------------------------------------------------
// Base(s) de donnees utilisee(s)
// --------------------------------------------------------------------------
        base = new BaseDeDonnees(
                "jdbc:sqlserver://mars;databasename=gnmi;" +
                "user=util_bip;password=x");
        base.setFormatDate("dd/MM/yyyy");
    }

// --------------------------------------------------------------------------
// Methode de lecture du contact
// --------------------------------------------------------------------------
    public Contact lireContact(Integer numeroContact) throws RemoteException,
                                                             SQLException
    {
        Contact contact;
        AccesBase accesBase;
        ContactDAO contactDAO;
        
        accesBase = new AccesBase(base);
        contactDAO = new ContactDAO(accesBase);

// --------------------------------------------------------------------------
// Affichage de l'adresse de l'utilisateur "appelant"...
// La methode getClientHost est heritee de UnicastRemoteObject qui en herite
// lui-meme de RemteServer.
// --------------------------------------------------------------------------
        try
        {
            System.out.println(getClientHost());
        }
        catch (ServerNotActiveException e)
        {
        }

// --------------------------------------------------------------------------
// Lecture du contact
// --------------------------------------------------------------------------
        accesBase.getConnection();
        try
        {
            contact = new Contact();
            contact.setNumero(numeroContact);
            contactDAO.lire(contact);
        }
        finally
        {
            accesBase.closeConnection();
        }

        return contact;
    }
}