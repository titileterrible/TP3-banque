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

   
    
    
    
    
    public void loadCompte () {
       
        System.out.println("compteDetailsMBean.loadCompte()");
        this.compte = compteManager.getCompte(id);
       
   }

    
    
    
    
    
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
    
    
    

    
    
    
    
    
    
    
    
    // ----------------------------------
    
    /**
     * Creates a new instance of CompteDetailsMBean
     */
    public CompteDetailsMBean() {
    }
}
