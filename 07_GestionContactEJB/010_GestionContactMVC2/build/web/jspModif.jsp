<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@page import="metierMapping.*, java.util.*" %>

<!DOCTYPE html>

<% Contact contact = (Contact) session.getAttribute("contact");
    Vector<Secteur> vSect = (Vector<Secteur>) session.getAttribute("vSect");
    String message = (String) session.getAttribute("message");
    String valueInput;
%>

<html>
    <head>
        <title>Modification d'un contact</title>
        <meta http-equiv="Content-Type"
              content="text/html; charset=utf-8" />
        <link rel="stylesheet" 
              type="text/css"
              href="miseEnPage.css" />
    </head>

    <body>
        <form action="ServletControleur?idEcran=2" method="post">
            <fieldset>
                <legend>
                    Modification du contact <%= contact.getNumero()%>
                </legend>

                <div class="divSaisieModif">
                    <% valueInput = "";
                        if (contact.getNom() != null)
                        {
                            valueInput = contact.getNom();
                        }
                    %>
                    <label for="nom">Nom :</label>
                    <input type="text" 
                           name="nom" 
                           value="<%=valueInput%>" 
                           size="20" 
                           maxlength="20" 
                           id="nom" />
                </div>

                <div class="divSaisieModif">
                    <% valueInput = "";
                        if (contact.getAdresse() != null)
                        {
                            valueInput = contact.getAdresse();
                        }
                    %>
                    <label for="adresse">Adresse :</label>
                    <input type="text" 
                           name="adresse" 
                           value="<%=valueInput%>"
                           size="50" 
                           maxlength="50" 
                           id="adresse" />                
                </div>

                <div class="divSaisieModif">
                    <% valueInput = "";
                        if (contact.getCodePostal() != null)
                        {
                            valueInput = contact.getCodePostal();
                        }
                    %>
                    <label for="codePostal">Code Postal :</label>
                    <input type="text" 
                           name="codePostal" 
                           value="<%=valueInput%>" 
                           size="5" 
                           maxlength="5" 
                           id="codePostal" />                
                </div>

                <div class="divSaisieModif">
                    <% valueInput = "";
                        if (contact.getVille() != null)
                        {
                            valueInput = contact.getVille();
                        }
                    %>
                    <label for="ville">Ville :</label>
                    <input type="text" 
                           name="ville" 
                           value="<%=valueInput%>"  
                           size="20" 
                           maxlength="20" 
                           id="ville" />                
                </div>

                <div class="divSaisieModif">
                    <label for="codeSecteur">Code secteur :</label>
                    <select name="codeSecteur" id="codeSecteur">
                    <%  if (contact.getCodeSecteur() == null)
                        {
                    %>
                        <option selected="selected"></option>
                        <%
                            for (int i = 0; i < vSect.size(); i++)
                            {
                        %>
                        <option><%= vSect.get(i).getCode() %></option>
                        <%
                            }
                        }
                        else
                        {
                        %>
                        <option></option>
                        <%
                            for (int i = 0; i < vSect.size(); i++)
                            {
                                if(contact.getCodeSecteur().
                                        compareTo(vSect.get(i).getCode()) == 0)
                                {
                        %>
                        <option selected="selected">
                            <%= vSect.get(i).getCode() %>
                        </option>
                        <%
                                }
                                else
                                {
                        %>
                        <option><%= vSect.get(i).getCode() %></option>
                        <%
                                }
                            }
                        }
                        %>
                    </select>
                    
                </div>
            </fieldset>

            <div>
                <input type="submit" 
                       class="envoyer" 
                       name="choixAction" 
                       value="Enregistrer"/>
                <input type="submit" 
                       class="envoyer" 
                       name="choixAction" 
                       value="Annuler"/>
            </div>
        </form>


        <div>
            <br />
            <br />
            <br />
            <p id=message><%=message%></p>
        </div>
    </body>
</html>
