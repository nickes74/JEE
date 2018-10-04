<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@page import="metierMapping.*"%>

<!DOCTYPE html>

<% Contact contact = (Contact) session.getAttribute("contact");
%>

<html>
    <head>
        <title>Enregistrement de la modification</title>
        <meta http-equiv="Content-Type"
              content="text/html; charset=utf-8" />
        <link rel="stylesheet" 
              type="text/css"
              href="miseEnPage.css" />
    </head>

    <body>
        <h2>
            Enregistrement du contact <%= contact.getNumero()%> effectué
        </h2>

        <br />
        
        <h2>
            Récapitulatif des données entrées
        </h2>
        <p>
            <%= contact.getNom() %>
        </p>
        <p>
            <%= contact.getAdresse()%>
        </p>
        <p>
            <%= contact.getCodePostal()%> 
        </p>
        <p>
            <%= contact.getVille()%>
        </p>
        <p>
            Code secteur : <%= contact.getCodeSecteur()%>
        </p>

        <p>
            <a href="ServletControleur?idEcran=4">Retour au menu principal</a>
        </p>
    </body>
</html>
