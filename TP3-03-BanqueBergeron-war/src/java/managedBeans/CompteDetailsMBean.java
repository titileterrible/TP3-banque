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

    private long idCpt;
    private CompteBancaire cpt;
    @EJB
    CompteBancaireManager cptManager;

   public void loadCompte () {
       this.cpt = cptManager.getCompte(idCpt);
   }
    
    
    
    
    public long getIdCpt() {
        return idCpt;
    }

    public void setIdCpt(long idCpt) {
        this.idCpt = idCpt;
    }

    public CompteBancaire getCpt() {
        return cpt;
    }

    public void setCpt(CompteBancaire cpt) {
        this.cpt = cpt;
    }

    public CompteBancaireManager getCptManager() {
        return cptManager;
    }

    public void setCptManager(CompteBancaireManager cptManager) {
        this.cptManager = cptManager;
    }
    
    
    
    
    
    
    
    
    // ----------------------------------
    
    /**
     * Creates a new instance of CompteDetailsMBean
     */
    public CompteDetailsMBean() {
    }
}
