package objetDistant;

import javax.ejb.Stateless;

@Stateless
public class Trieur implements TrieurRemote 
{

    @Override
    public Comparable[] trier(Comparable tableau[])
    {
      boolean permut;
      int n;
      Comparable x;

      do
      {
         permut = false;
         for (n = 0; n < tableau.length - 1; n++)
         {
            if(tableau[n].compareTo(tableau[n + 1]) > 0)
            {
               x = tableau[n];
               tableau[n]= tableau[n + 1];
               tableau[n + 1] = x;
               permut= true;
            }
         }
      }
      while (permut == true);

      return tableau;
   }
 }
