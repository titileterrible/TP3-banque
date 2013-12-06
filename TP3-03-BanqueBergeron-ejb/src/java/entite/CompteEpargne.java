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
 * @author thierry
 */


@Entity
@DiscriminatorValue("CompteEpargne")
public class CompteEpargne extends CompteBancaire implements Serializable{
    
 private double interetAnnuel;
 private String typeCompte;

    public CompteEpargne() {
    }

    public CompteEpargne(String nom, double solde) {
        super(nom, solde);
        this.typeCompte = "Epargne";
        this.interetAnnuel = 10;
    }

    
    
 /*   
    
 Exercice 2 : calcul du service 
Ecrire un programme quisaisitun prixet letaux du service puis calcule et affiche letotalà payer et le
service.
Par exemple, sileprixest de 10 et letaux du service de 15%, leprogramme affichera 1.5€ pour le
service et 11.5€ pour letotal.
On considère que leprixsaisisera de type floattandis que leservice saisisera de type int. 
Pourafficherle caractère €,on utilisera sa valeurunicode :'\u20AC' 
Question 1 
Le service est calculé par laformule : prix*taux/100. 
Question 2 
Le service est calculé par laformule : prix*(taux/100). 
Solution 
Question 1
import util.Scanner;   java.
public classExo2{ 
public static void main(String[] args){ 
        Scanner input = newScanner(System.in); 
        System.out.print(" prix : "); 
        float prix = input.nextFloat(); 
        System.out.print(" taux : "); 
        int taux = input.nextInt(); 
        float service = prix*taux/100; 
        System.out.println(" service : "+service+'\u20AC'); 
        float total = prix+service; 
        System.out.println(" total : "+total+'\u20AC'); 
} 
} 
résultat 
prix : 56,80 
taux : 15 
service : 8.52€ 
total : 65.32€ 

* VARI – ED n°2 – premiers programmes – corrigé    2 
Question 2
Avec float service = prix*(taux/100); 
Résultat 
prix : 56,80 
taux : 15 
service : 0.0€ 
total : 56.8€    
    
 /**/      
   

@Override
public String toString() {
    return "Compte Epargne n°" + super.getId() + " ( " + super.getNom() + " ) ";
}

    public double getInteretAnnuel() {
        return interetAnnuel;
    }

    public void setInteretAnnuel(double interetAnnuel) {
        this.interetAnnuel = interetAnnuel;
    }

    
    public String getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }


}
