/* -------------------------------------------------------------------------- */
/* Script specifique a l'ecran d'accueil                                      */
/* -------------------------------------------------------------------------- */

window.onload = initForm;
window.onunload = function() {};

/* -------------------------------------------------------------------------- */
/* Le document html n'a qu'un formulaire. On n'utilise donc que la reference  */
/* 0 du tableau forms : document.forms[0]                                     */
/* -------------------------------------------------------------------------- */
function initForm()
{
    document.forms[0].onsubmit = validerAccueil;
}

function validerAccueil()
{
    var retour = true;

    var numero = document.getElementById("numero");
    var message = document.getElementById("message");
    var bouton = document.getElementById("radio4");
    
    numero.value = cadre(numero.value);

    if (!bouton.checked)
    {
        if ((isNaN(numero.value)) || (numero.value === ""))
        {
            retour = false;
            numero.focus();
            numero.className = "invalide";
            if(numero.value === "")
            {
                message.innerHTML = "Saisir un numéro de contact."
            }
            else
            {
                message.innerHTML =
                        "Le numéro est incorrect (ne taper que des chiffres).";
            }
        }
        else
        {
            numero.className = "";
        }
    }
    return retour;
}

function cadre(chaine)
{
   var resultat ="";
   var longueur = chaine.length;
   var pc = 0;
   var dc;
   var i;

   while((chaine.charAt(pc) === ' ') && (pc < longueur))
   {
      pc++;
   }

   if (pc < longueur)
   {
      dc = longueur - 1;

      while (chaine.charAt(dc) === ' ')
      {
         dc--;
      }

      for (i = pc; i <= dc; i++)
      {
         resultat += chaine.charAt(i);
      }
   }
   return resultat;
}
