/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author thierry  et Claudia
 */
@Entity
@DiscriminatorValue("CompteCourant")
public class CompteCourant extends CompteBancaire implements Serializable{

private String typeCompte;

public CompteCourant() {
}

public CompteCourant(String nom, double solde) {
    super(nom, solde);
    this.typeCompte = "Courant";
}

@Override
public String toString() {
    return "Compte Courant n°" + super.getId() + " ( " + super.getNom() + " ) ";
}

    
    public String getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }



}
