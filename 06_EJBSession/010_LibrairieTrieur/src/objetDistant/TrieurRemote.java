package objetDistant;

import javax.ejb.Remote;

@Remote
public interface TrieurRemote {
    public Comparable[] trier(Comparable tableau[]);
   
}
