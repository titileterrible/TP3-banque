/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entite.CompteBancaire;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import sessionBeans.CompteBancaireManager;

/**
 *
 * @author thierry
 * BackingBean pour compte-details.xhtml
 */
@Named(value = "compteDetailsMBean")
//@Dependent
@ViewScoped
public class CompteDetailsMBean {

    private long id;
    private CompteBancaire compte;
    @EJB
    CompteBancaireManager compteManager;

    /*
    public void loadCompte () {
       
        System.out.println("compteDetailsMBean.loadCompte()");
        this.compte = compteManager.getCompte(id);
       
   }
    /**/

    // ---------- Adapté du TP1 -------
    
    // Renvoie les détails du compte courant (celui dans l'attribut compte de 
    // cette classe), qu'on appelle une propriété (property) 
     public CompteBancaire getDetails() {  
       return compte;  
     } 
    
    
    
    /*
    
      
    // Renvoie les détails du client courant (celui dans l'attribut customer de 
    // cette classe), qu'on appelle une propriété (property) 
     public Customer getDetails() {  
       return customer;  
     }  
  
    // Action handler - met à jour la base de données en fonction du client passé 
    // en paramètres, et renvoie vers la page qui affiche la liste des clients.   
    public String update() {  
      System.out.println("###UPDATE###");  
      customer = customerManager.updateCustomer(customer);  
      return "CustomerList";  
    }  
  
  
    // Action handler - renvoie vers la page qui affiche la liste des clients   
    public String list() {  
      System.out.println("###LIST###");  
      return "CustomerList";  
    }   
    
   // ------------------------------------- 
   /**/ 
    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setCompte(CompteBancaire compte) {
        this.compte = compte;
    }

    public CompteBancaireManager getCompteManager() {
        return compteManager;
    }

    public void setCompteManager(CompteBancaireManager compteManager) {
        this.compteManager = compteManager;
    }
    /**/
    
    
    

    
    
    
    
    
    
    
    
    // ----------------------------------
    
    /**
     * Creates a new instance of CompteDetailsMBean
     */
    public CompteDetailsMBean() {
    }
}
