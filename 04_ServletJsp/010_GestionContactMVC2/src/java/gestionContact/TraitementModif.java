package gestionContact;

import daoJdbcMapping.*;
import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metierMapping.*;
import utilitairesMG.jdbc.AccesBase;
import utilitairesMG.jdbc.BaseDeDonnees;

public class TraitementModif
{
    private BaseDeDonnees base;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public TraitementModif(BaseDeDonnees base)
    {
        this.base = base;
    }

// --------------------------------------------------------------------------
// Traitement d’annulation de la modification
// --------------------------------------------------------------------------
    public String annulationModif(HttpServletRequest request)
    {
        String jspRetour;

        Contact contact;
        HttpSession session = request.getSession();

        contact = (Contact) session.getAttribute("contact");

        jspRetour = "/jspAccueil.jsp";
        session.setAttribute("message", "Modification annulée");
        session.setAttribute("numeroContact", contact.getNumero().toString());
        session.setAttribute("choixAction", "modification");

        return jspRetour;
    }

// --------------------------------------------------------------------------
// Enregistrement de la modification et affichage de l'ecran de confirmation
// de la modification
// --------------------------------------------------------------------------
    public String enregModif(HttpServletRequest request)
    {
        String jspRetour;

        Contact contact;
        Vector<Secteur> vSect;

        HttpSession session = request.getSession();
        
        AccesBase accesBase;
        ContactDAO contactDAO;

        contact = (Contact) session.getAttribute("contact");
        vSect = (Vector<Secteur>) session.getAttribute("vSect");

        String nom = request.getParameter("nom");
        nom = nom.trim();
        if (nom.compareTo("") == 0)
        {
            nom = null;
        }

        String adresse = request.getParameter("adresse");
        adresse = adresse.trim();
        if (adresse.compareTo("") == 0)
        {
            adresse = null;
        }

        String codePostal = request.getParameter("codePostal");
        codePostal = codePostal.trim();
        if (codePostal.compareTo("") == 0)
        {
            codePostal = null;
        }

        String ville = request.getParameter("ville");
        ville = ville.trim();
        if (ville.compareTo("") == 0)
        {
            ville = null;
        }

        String stringCodeSecteur = request.getParameter("codeSecteur");
        Integer codeSecteur = null;
        if (stringCodeSecteur.compareTo("") != 0)
        {
            codeSecteur = new Integer(stringCodeSecteur);
        }

// --------------------------------------------------------------------------
// Modification de l'objet contact
// --------------------------------------------------------------------------
        contact.setNom(nom);
        contact.setAdresse(adresse);
        contact.setCodePostal(codePostal);
        contact.setVille(ville);
        contact.setCodeSecteur(codeSecteur);

        accesBase = new AccesBase(base);

        try
        {
            accesBase.getConnection();
            contactDAO = new ContactDAO(accesBase);

            try
            {
                int retour = contactDAO.modifier(contact);
                if (retour != 0)
                {
                    jspRetour = "/jspRecap.jsp";
                    session.setAttribute("contact", contact);
                }
                else
                {
                    jspRetour = "/jspModif.jsp";
                    session.setAttribute("message", "Le contact "
                                         + contact.getNumero()
                                         + " a été supprimé");
                    session.setAttribute("contact", contact);
                    session.setAttribute("vSect", vSect);
                }
            }
            finally
            {
                accesBase.closeConnection();
            }
        }
        catch (SQLException e)
        {
            jspRetour = "/jspModif.jsp";
            session.setAttribute("message", e.getMessage());
            session.setAttribute("contact", contact);
            session.setAttribute("vSect", vSect);
        }

        return jspRetour;
    }
}
