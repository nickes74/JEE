package gestionContact;

// ==========================================================================
// ServletControleur.java : servlet d'accueil du projet gestionContactMVC2
// ==========================================================================
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import utilitairesMG.jdbc.*;
import objetDistant.MappingRMI;

public class ServletControleur extends HttpServlet
{
    private TraitementAccueil traitementAccueil;
    private TraitementModif traitementModif;
    private BaseDeDonnees base;

    @Override
    public void init()
    {   
        MappingRMI map = null;
        String url = "//localhost:6128";
        
        try
        {
            map = (MappingRMI)Naming.lookup(url + "/mappingDistant");
            
        } catch (NotBoundException | MalformedURLException | RemoteException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        traitementAccueil = new TraitementAccueil(map);
        traitementModif = new TraitementModif(map);        
    }

// --------------------------------------------------------------------------
// Traitement du formulaire d'accueil (index.jsp)
// --------------------------------------------------------------------------
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
        throws ServletException, IOException, RemoteException, SQLException
    {

// --------------------------------------------------------------------------
// contexte   : ServletContext pour utiliser le dispatcher.
// dispatcher : pour acceder aux jsp d'affichage.
// --------------------------------------------------------------------------
        ServletContext contexte;
        RequestDispatcher dispatcher;

// --------------------------------------------------------------------------
// idEcran    : identifiant de l'ecran reçu.
// jsp        : jsp a afficher (retournee par les methodes de Traitement.
// choixAction : action choisie sur l'ecran d'accueil.
// --------------------------------------------------------------------------
        Integer idEcran;
        String jsp;
        String choixAction;
        HttpSession session;

// --------------------------------------------------------------------------
// Indication du codage pour l'interpretation des caracteres recus par la
// requete.
// --------------------------------------------------------------------------
        request.setCharacterEncoding("UTF-8");

// --------------------------------------------------------------------------
// Recuperation du SerletContext pour dispatcher...
// --------------------------------------------------------------------------
        contexte = getServletContext();

// --------------------------------------------------------------------------
// Lecture de l'identifiant de l'ecran (champ cache ou parametre d'index.jsp)
// --------------------------------------------------------------------------
        String numeroEcran = request.getParameter("idEcran");
        idEcran = new Integer(numeroEcran);

// --------------------------------------------------------------------------
// Divers branchements suivant l'ecran qui vient d'appeler ServletControleur
// --------------------------------------------------------------------------
        switch (idEcran)
        {

// --------------------------------------------------------------------------
// On vient de l'ecran jspAccueil
// --------------------------------------------------------------------------
            case 1:
                choixAction = request.getParameter("choixAction");

                if (choixAction.compareTo("liste") == 0)
                {
                    jsp = traitementAccueil.traitementListe(request);
                }
                else
                {
                    if (choixAction.compareTo("modification") == 0)
                    {
                        jsp = traitementAccueil.traitementModif(request);
                    }
                    else
                    {
                        jsp = traitementAccueil.traitementNonRealise(request);
                    }
                }
                break;

// --------------------------------------------------------------------------
// On vient de l'ecran jspModif
// --------------------------------------------------------------------------
            case 2:
                choixAction = request.getParameter("choixAction");

                if (choixAction.compareTo("Annuler") == 0)
                {
                    jsp = traitementModif.annulationModif(request);
                }
                else
                {
                    jsp = traitementModif.enregModif(request);
                }
                break;

            default:
                session = request.getSession();
                session.setAttribute("message", "");
                session.setAttribute("numeroContact", "");
                session.setAttribute("choixAction", "liste");

                jsp = "/jspAccueil.jsp";
        }

        dispatcher = contexte.getRequestDispatcher(jsp);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
        throws ServletException, IOException, RemoteException
    {
        try
        {
            processRequest(request, response);
        } catch (SQLException ex)
        {
            Logger.getLogger(ServletControleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
        throws ServletException, IOException, RemoteException
    {
        try
        {
            processRequest(request, response);
        } catch (SQLException ex)
        {
            Logger.getLogger(ServletControleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
