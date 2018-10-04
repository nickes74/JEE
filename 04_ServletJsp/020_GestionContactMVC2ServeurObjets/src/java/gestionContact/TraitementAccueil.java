package gestionContact;

import daoServeurObjets.*;
import java.io.IOException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metierMapping.*;
import utilitairesMG.divers.*;

public class TraitementAccueil
{
   private PriseServeur priseServeur;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
   public TraitementAccueil(PriseServeur priseServeur)
   {
      this.priseServeur = priseServeur;
   }

// --------------------------------------------------------------------------
// Traitement d'affichage de la liste
// --------------------------------------------------------------------------
   public String traitementListe(HttpServletRequest request)
   {
      String jspRetour;
      Vector<Contact> listeContacts;
      Vector<Colonne> listeColonnes;
      HttpSession session = request.getSession();
      ContactDAO contactDAO;
      AccesServeur accesServeur = new AccesServeur(priseServeur);

      try
      {
         accesServeur.getConnection();
         contactDAO = new ContactDAO(accesServeur);

         try
         {
            listeContacts = contactDAO.lireListe();
            listeColonnes = contactDAO.getListeColonnes();

            jspRetour = "/jspListe.jsp";
            session.setAttribute("message", "");
            session.setAttribute("listeContacts", listeContacts);
            session.setAttribute("listeColonnes", listeColonnes);
         }
         catch (ClassNotFoundException | IOException e)
         {
            jspRetour = "/jspAccueil.jsp";
            session.setAttribute("message", "Erreur : " + e.getMessage());
            session.setAttribute("numeroContact", "");
            session.setAttribute("choixAction", "liste");
         }
         finally
         {
            accesServeur.closeConnection();
         }
      }
      catch(IOException e)
      {
         jspRetour = "/jspAccueil.jsp";
         session.setAttribute("message", "Erreur : Serveur Objets indisponible");
         session.setAttribute("numeroContact", "");
         session.setAttribute("choixAction", "liste");
      }

      return jspRetour;
    }

// --------------------------------------------------------------------------
// Traitement d'affichage de l'ecran de modification
// --------------------------------------------------------------------------
   public String traitementModif(HttpServletRequest request)
   {
      String jsp;

      Integer numeroContact;
      Contact contact;
      Vector<Secteur> vSect;
      HttpSession session = request.getSession();
      ContactDAO contactDAO;
      SecteurDAO secteurDAO;
      AccesServeur accesServeur = new AccesServeur(priseServeur);

      String chaineNumeroContact = request.getParameter("numeroContact");

      try
      {
         accesServeur.getConnection();
         contactDAO = new ContactDAO(accesServeur);
         secteurDAO = new SecteurDAO(accesServeur);

         try
         {
            numeroContact = Integer.parseInt(chaineNumeroContact);
            contact = new Contact();
            contact.setNumero(numeroContact);
            contactDAO.lire(contact);

            vSect = secteurDAO.lireListe();

            jsp = "/jspModif.jsp";
            session.setAttribute("message", "");
            session.setAttribute("contact", contact);
            session.setAttribute("vSect", vSect);
         }
         catch(ClassNotFoundException e)
         {
            jsp = "/jspAccueil.jsp";
            session.setAttribute("message", "Erreur : " + e.getMessage());
            session.setAttribute("numeroContact", chaineNumeroContact);
            session.setAttribute("choixAction", "modification");
         }
         finally
         {
            accesServeur.closeConnection();
         }
      }
      catch(IOException e)
      {
         jsp = "/jspAccueil.jsp";
         session.setAttribute("message", "Erreur : Serveur Objets indisponible");
         session.setAttribute("numeroContact", chaineNumeroContact);
         session.setAttribute("choixAction", "modification");
      }

      return jsp;
   }

// --------------------------------------------------------------------------
// Traitement d'affichage du message non realise sur l'ecran d'accueil
// --------------------------------------------------------------------------
   public String traitementNonRealise(HttpServletRequest request)
   {
      String jspRetour;
      HttpSession session = request.getSession();

      String choixAction = request.getParameter("choixAction");
      String chaineNumeroContact = request.getParameter("numeroContact");

      jspRetour = "/jspAccueil.jsp";
      session.setAttribute("message", "Ecran de " + choixAction + " non réalisé");
      session.setAttribute("choixAction", choixAction);
      session.setAttribute("numeroContact", chaineNumeroContact);

      return jspRetour;
   }
}
