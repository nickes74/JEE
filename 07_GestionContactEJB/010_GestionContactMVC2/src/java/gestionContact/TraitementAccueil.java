package gestionContact;

import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metierMapping.*;
import objetDistant.MappingRemote;
import utilitairesMG.divers.Colonne;


public class TraitementAccueil
{
    private MappingRemote map;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public TraitementAccueil(MappingRemote map)
    {
        this.map = map;
    }

// --------------------------------------------------------------------------
// Traitement d'affichage de la liste
// --------------------------------------------------------------------------
    public String traitementListe(HttpServletRequest request)
    {
        String jspRetour;
        
        Vector<Vector>listeCont;
        Vector<Contact> listeContacts;
        Vector<Colonne> listeColonnes;
        HttpSession session = request.getSession();
//        
//        listeCont = new Vector<Vector>();
//        listeContacts = new Vector<Contact>();
//        listeColonnes = new Vector<Colonne>();

// --------------------------------------------------------------------------
// L'objet ContactDAO est une variable locale de la methode. Elle est creee a
// chaque appel (et liberee a la fin). Il s'agit d'eviter le melange de
// donnees entre plusieurs utilisateurs. En effet, la ServletControleur est
// instanciée une fois. La classe TraitementAccueil une fois également. Si
// l'objet ContactDAO etait declare en propriete de la classe
// TraitementAccueil, elle serait commune a tous les utilisateurs. Or, un
// objet ContactDAO contient une propriete de type JeuResultat qui est
// modifiee a chaque lecture dans la base.
// --------------------------------------------------------------------------
        try
        {

            listeCont = map.lireListeContacts();
            listeContacts = listeCont.elementAt(0);
            listeColonnes = listeCont.elementAt(1);

                jspRetour = "/jspListe.jsp";
                session.setAttribute("listeContacts", listeContacts);
                session.setAttribute("listeColonnes", listeColonnes);
        

        }
        catch (SQLException e)
        {
            jspRetour = "/jspAccueil.jsp";
            session.setAttribute("message", e.getMessage());
            session.setAttribute("numeroContact", "");
            session.setAttribute("choixAction", "liste");
        }
        return jspRetour;
    }

// --------------------------------------------------------------------------
// Traitement d'affichage de l'ecran de modification
// --------------------------------------------------------------------------
    public String traitementModif(HttpServletRequest request) throws SQLException
    {
        String jspRetour;

        Contact contact;
        Integer numeroContact;
        Vector<Vector> listVec;
        Vector<Secteur> vSect;
        HttpSession session = request.getSession();

        String chaineNumeroContact = request.getParameter("numeroContact");
      
            try
            {
                numeroContact = new Integer(chaineNumeroContact);
                contact = new Contact();
                contact = map.lireContact(numeroContact);

                listVec = map.lireListeSecteur();
                vSect = listVec.elementAt(0);

                jspRetour = "/jspModif.jsp";
                session.setAttribute("message", "");
                session.setAttribute("contact", contact);
                session.setAttribute("vSect", vSect);
            }
            catch (NumberFormatException e)
            {
                jspRetour = "/jspAccueil.jsp";
                session.setAttribute("message", e.getMessage());
                session.setAttribute("numeroContact", chaineNumeroContact);
                session.setAttribute("choixAction", "modification");
            }
 

        return jspRetour;
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
        session.setAttribute("message", 
                             "Ecran de " + choixAction + " non réalisé");
        session.setAttribute("choixAction", choixAction);
        session.setAttribute("numeroContact", chaineNumeroContact);

        return jspRetour;
    }
}
