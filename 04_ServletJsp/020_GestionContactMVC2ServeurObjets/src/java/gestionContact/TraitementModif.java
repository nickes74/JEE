package gestionContact;

import daoServeurObjets.*;
import java.io.IOException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metierMapping.*;

public class TraitementModif
{
    private PriseServeur priseServeur;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public TraitementModif(PriseServeur priseServeur)
    {
        this.priseServeur = priseServeur;
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
// Traitement d'enregistrement de la modification et affichage de l'ecran de
// confirmation de la modification
// --------------------------------------------------------------------------
    public String enregModif(HttpServletRequest request)
    {
        String jspRetour;
        Contact contact;
        HttpSession session = request.getSession();
        AccesServeur accesServeur = new AccesServeur(priseServeur);

        ContactDAO contactDAO;

        contact = (Contact) session.getAttribute("contact");

        String nom = request.getParameter("nom");
        if (nom.compareTo("") == 0)
        {
            nom = null;
        }

        String adresse = request.getParameter("adresse");
        if (adresse.compareTo("") == 0)
        {
            adresse = null;
        }

        String codePostal = request.getParameter("codePostal");
        if (codePostal.compareTo("") == 0)
        {
            codePostal = null;
        }

        String ville = request.getParameter("ville");
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

        try
        {
            accesServeur.getConnection();
            contactDAO = new ContactDAO(accesServeur);

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

                    Vector<Secteur> vSect =
                        (Vector<Secteur>) session.getAttribute("vSect");
                    session.setAttribute("contact", contact);
                    session.setAttribute("vSect", vSect);
                    session.setAttribute("message", "Contact supprimé");
                }
            }
            catch (ClassNotFoundException | IOException e)
            {
                jspRetour = "/jspModif.jsp";

                Vector<Secteur> vSect =
                    (Vector<Secteur>) session.getAttribute("vSect");
                session.setAttribute("contact", contact);
                session.setAttribute("vSect", vSect);
                session.setAttribute("message", "Erreur : " + e.getMessage());
            }
            finally
            {
                accesServeur.closeConnection();
            }
        }
        catch (IOException e)
        {
            jspRetour = "/jspModif.jsp";

            Vector<Secteur> vSect =
                (Vector<Secteur>) session.getAttribute("vSect");
            session.setAttribute("contact", contact);
            session.setAttribute("vSect", vSect);
            session.setAttribute("message", 
                                 "Erreur : Serveur Objets indisponible");
        }

        return jspRetour;
    }
}
