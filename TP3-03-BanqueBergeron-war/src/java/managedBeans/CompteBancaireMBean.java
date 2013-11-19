/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entite.CompteBancaire;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import sessionBeans.CompteBancaireManager;

/**
 *
 * @author thierry
 */
@Named(value = "compteBancaireMBean")
//@SessionScoped
@ViewScoped
public class CompteBancaireMBean implements Serializable {
    
    // toto et tata sont dans un bateau..
    // Variable d'instance pour conserver la liste des comptes
    // pour les colonnes de tableau triables avec PrimeFaces
    private List<CompteBancaire> compteBancaireList;
    
    @EJB
    private CompteBancaireManager cptManager;
    
    // ----------------------
    
    // Création de la propriété pour conserver le compte
    private CompteBancaire compte;

    // Action handler - appelé lorsque l'utilisateur sélectionne une ligne dans 
    // la DataTable pour voir les détails
    // Note : Doit retourner le nom exact de la page xhtml apellée
    /*
    public String showDetails(long id) {
    return "compteDetails?faces-redirect=true;includeViewParams=true;id=" + id;    
    }
    /**/
    public List<CompteBancaire> getCompteBancaireList() {
        return compteBancaireList;
    }

    public void setCompteBancaireList(List<CompteBancaire> compteBancaireList) {
        this.compteBancaireList = compteBancaireList;
    }

    public CompteBancaireManager getCptManager() {
        return cptManager;
    }

    public void setCptManager(CompteBancaireManager cptManager) {
        this.cptManager = cptManager;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setCompte(CompteBancaire compte) {
        this.compte = compte;
    }
    
    
   
    public CompteBancaire getDetails() {  
        // System.out.println(compte.toString());
        return compte;  
    } 
    
       
    public String showDetails(CompteBancaire compte) {  
        this.compte = compte;  
        return "compteDetails2?faces-redirect=true";  
    } 

    
  
 
  
    
   
   
   
   // -----------------------

    
    // Création d'une instance du managed bean
    public CompteBancaireMBean() {
    }
    
    
    // Renvoie la liste des comptes pour affichage dans une datatable
    public List<CompteBancaire> getAllCpts() {
        return cptManager.getAllComptes();
    }
    
    // Idem, mais avec des paramètres de pagination
    public List<CompteBancaire> getCptsByRange(int offset, int qte) {
        return cptManager.getAllComptes();
    }
    

    
    
    
}
