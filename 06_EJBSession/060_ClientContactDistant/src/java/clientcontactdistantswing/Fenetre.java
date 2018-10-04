package clientcontactdistantswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import metierMapping.Contact;

public class Fenetre extends JFrame implements ActionListener
{
    private JPanel panneauFond;

// --------------------------------------------------------------------------
// Panneau du haut : saisie du numero de contact
// --------------------------------------------------------------------------
    private JPanel panneauSaisie;
    private JLabel libelleSaisie;
    private JTextField saisie;

// --------------------------------------------------------------------------
// Panneau du bas : affichage du contact
// --------------------------------------------------------------------------
    private JPanel panneauAffichage;
    private JScrollPane defileur;
    
    private JPanel panneauNom;
    private JPanel panneauAdresse;
    private JPanel panneauCodePostal;
    private JPanel panneauVille;
    private JPanel panneauCodeSecteur;
    
    private JLabel nom;
    private JLabel adresse;
    private JLabel codePostal;
    private JLabel ville;
    private JLabel codeSecteur;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public Fenetre(String s)
    {
        super(s);
        addWindowListener(new EcouteWindowClosing());

        panneauFond = new JPanel();
        panneauFond.setLayout(new BorderLayout());

// --------------------------------------------------------------------------
// Panneau du haut : saisie
// --------------------------------------------------------------------------
        panneauSaisie = new JPanel();
        panneauSaisie.setLayout(new FlowLayout(FlowLayout.LEFT));
        panneauSaisie.setBackground(new Color(220, 220, 255));

        libelleSaisie = new JLabel("Numéro du contact : ");
        saisie = new JTextField("", 5);
        saisie.addActionListener(this);

        panneauSaisie.add(libelleSaisie);
        panneauSaisie.add(saisie);

// --------------------------------------------------------------------------
// Panneau du bas : panneauAffichage contact
// --------------------------------------------------------------------------
        panneauAffichage = new JPanel();
        panneauAffichage.setLayout(new GridLayout(5, 1));
        panneauAffichage.setPreferredSize(new Dimension(400, 160));

        panneauNom = new JPanel();
        panneauNom.setLayout(new FlowLayout(FlowLayout.LEFT));
        panneauNom.setBackground(Color.white);
        nom = new JLabel("");
        panneauNom.add(nom);
        
        panneauAdresse = new JPanel();
        panneauAdresse.setLayout(new FlowLayout(FlowLayout.LEFT));
        adresse = new JLabel("");
        panneauAdresse.add(adresse);
        
        panneauCodePostal = new JPanel();
        panneauCodePostal.setLayout(new FlowLayout(FlowLayout.LEFT));
        panneauCodePostal.setBackground(Color.white);
        codePostal = new JLabel("");
        panneauCodePostal.add(codePostal);
        
        panneauVille = new JPanel();
        panneauVille.setLayout(new FlowLayout(FlowLayout.LEFT));
        ville = new JLabel("");
        panneauVille.add(ville);
        
        panneauCodeSecteur = new JPanel();
        panneauCodeSecteur.setLayout(new FlowLayout(FlowLayout.LEFT));
        panneauCodeSecteur.setBackground(Color.white);
        codeSecteur = new JLabel("");
        panneauCodeSecteur.add(codeSecteur);
        
        panneauAffichage.add(panneauNom);
        panneauAffichage.add(panneauAdresse);
        panneauAffichage.add(panneauCodePostal);
        panneauAffichage.add(panneauVille);
        panneauAffichage.add(panneauCodeSecteur);
        
        defileur = new JScrollPane(panneauAffichage);

        panneauFond.add(panneauSaisie, BorderLayout.NORTH);
        panneauFond.add(defileur);

        getContentPane().add(panneauFond);

        pack();
        setVisible(true);
    }

// --------------------------------------------------------------------------
// Methode d'ecoute du JTextField   
// --------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e)
    {
        String texteNumero;

// --------------------------------------------------------------------------
// Recuperation du numero de contact saisi
// --------------------------------------------------------------------------
        texteNumero = saisie.getText();
        Controleur.chercheContact(texteNumero);
    }

// --------------------------------------------------------------------------
// Affichage du contact
// --------------------------------------------------------------------------
    public void afficheContact(Contact contact)
    {
        nom.setForeground(Color.black);
        nom.setText(contact.getNom());
        adresse.setText(contact.getAdresse());
        codePostal.setText(contact.getCodePostal());
        ville.setText(contact.getVille());
        if(contact.getCodeSecteur() != null)
        {
            codeSecteur.setText(contact.getCodeSecteur().toString());
        }
        else
        {
            codeSecteur.setText(null);
        }
    }
    
// --------------------------------------------------------------------------
// Affichage du message d'erreur
// --------------------------------------------------------------------------
    public void afficheMessage(String messageErreur)
    {
        nom.setText(messageErreur);
        nom.setForeground(Color.red);
        
        adresse.setText(null);
        codePostal.setText(null);
        ville.setText(null);
        codeSecteur.setText(null);
    }

// ==========================================================================
// Classe interne pour le traitement de la fermeture de la fenetre  
// ==========================================================================
    private class EcouteWindowClosing extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            Controleur.arreter();
        }
    }
}
