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
    
    private List<CompteBancaire> compteBancaireList;
    
    @EJB
    private CompteBancaireManager cptManager;
    
    // ----------------------
    
    // Création de la propriété pour conserver le compte
    private CompteBancaire compte;
    
    // Selection de ligne pour afficher 
    // Les détails d'un compte
    public String showDetails(long id) {
        return "compteDetails?id=" + id;    
    }
    
    // -----------------------

    
    /**
     * Creates a new instance of CompteBancaireMBean
     */
    public CompteBancaireMBean() {
    }
    
    
    
    public List<CompteBancaire> getAllCpts() {
        return cptManager.getAllComptes();
    }
    
    
    public List<CompteBancaire> getCptsByRange(int offset, int qte) {
        return cptManager.getAllComptes();
    }
    

    
    
    
}
