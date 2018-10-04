package clientcontactdistantswing;

import java.sql.SQLException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import metierMapping.Contact;
import objetDistant.ContactDistantRemote;
import utilitairesMG.graphique.LF;

public class Controleur
{
    private static ContactDistantRemote contactDistant;
    private static Fenetre maFenetre;
    
    public static void main(String[] args) throws NamingException
    {
        Hashtable variablesEnv = new Hashtable();
        variablesEnv.put("org.omg.CORBA.ORBInitialHost", "94010-6101877");
        variablesEnv.put("org.omg.CORBA.ORBInitialPort", "3700");

        Context ctx = new InitialContext(variablesEnv);
        contactDistant = 
            (ContactDistantRemote)ctx.lookup("jndiContactDistant");
        
        javax.swing.SwingUtilities.invokeLater(
        new Runnable()
        {
            public void run()
            {
                LF.setLF();
                maFenetre = new Fenetre("LectureContactEJB");
            }
        }
        );
    }

    public static void chercheContact(String texteNumero)
    {
        Integer numero;
        Contact contact;
        
        try
        {
            numero = new Integer(texteNumero);
            contact = contactDistant.lireContact(numero);
            
            maFenetre.afficheContact(contact);
        }
        catch(NumberFormatException | SQLException  e)
        {
            maFenetre.afficheMessage(e.getMessage());
        }
    }
    
    public static void arreter()
    {
        System.exit(0);
    }
}
