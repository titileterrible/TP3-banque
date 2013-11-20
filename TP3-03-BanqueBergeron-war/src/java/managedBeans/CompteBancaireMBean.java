/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entite.CompteBancaire;
import entite.OperationBancaire;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import sessionBeans.CompteBancaireManager;

/**
 *
 * @author thierry
 */
@Named(value = "compteBancaireMBean")
@SessionScoped
//@ViewScoped
public class CompteBancaireMBean implements Serializable {
    
    
    
    @EJB
    private CompteBancaireManager cptManager;
    
    //-----------------Claudia-----------------------------------------------------
    // CompteBancaire courant dans la session, utilisé pour afficher ses détails ou
    // pour faire une mise à jour du compte modifié dans la base
    private CompteBancaire compteBancaire;
    private int id;
   //-----------------Claudia-----------------------------------------------------
    
    private String nom;
    private int solde;
    private List<CompteBancaire> custList = null;
    
    
    // Création instance de CompteBancaireMBean
    public CompteBancaireMBean() {
        System.out.println("compteBancaireMBean.CompteBancaireMBean()"); 
        System.out.println("BEAN CONSTRUIT !");
    }
    
    
    public List<CompteBancaire> getAllCpts() {
        System.out.println("compteBancaireMBean.getAllCpts()");  
        return cptManager.getAllComptes();
    }
    
    
    public List<CompteBancaire> getCptsByRange(int offset, int qte) {
        System.out.println("compteBancaireMBean.getAllCpts(offset,qte)"); 
        return cptManager.getAllComptes();
    }
    
    
    
    //------------ Thierry ---------
    
    // DEBUGG >>> OK
    public Collection<OperationBancaire> getOperations() {
        System.out.println("compteBancaireMBean.getOperations()");
        Collection<OperationBancaire> operations = compteBancaire.getOperations();
        return operations;
    }
    
    
   // DEBUGG >>> OK 
   public void updateCompte() {
        System.out.println("compteBancaireMBean.updateCompte()");
        compteBancaire = cptManager.update(compteBancaire);
        //return "comptes-List?faces-redirect=true";
    }
    
   // DEBUGG >>> OK
    public String deleteCompte() {  
        System.out.println("compteBancaireMBean.deleteCompte()");  
        cptManager.deleteCompte(compteBancaire);
        // MAJ
        //this.refresh();
        return "comptes-List?faces-redirect=true"; 
    }  
    
    
    // Methode vide... provisoire 
   public void retrait1(){
       System.out.println("compteBancaireMBean.retrait1()");
       //
   } 
    
   // Methode vide... provisoire 
   public void depot1(){
       System.out.println("compteBancaireMBean.depot1()");
       //
   }  
    
     
    
   //-----------------Claudia --------------------------------------------------
    
    


    public int getId() {
        System.out.println("compteBancaireMBean.getId()");
        return id;
    }

    public void setId(int id) {
        System.out.println("compteBancaireMBean.setId(id)");
        this.id = id;
    }

    public String getNom() {
        System.out.println("compteBancaireMBean.getNom()");
        return nom;
    }

    public void setNom(String nom) {
        System.out.println("compteBancaireMBean.setNom(nom)");
        this.nom = nom;
    }

    public int getSolde() {
        System.out.println("compteBancaireMBean.getSolde()");
        return solde;
    }

    public void setSolde(int solde) {
        System.out.println("compteBancaireMBean.setSolde(solde)");
        this.solde = solde;
    }

    public CompteBancaireManager getCptManager() {
        System.out.println("compteBancaireMBean.getCptManager()");
        return cptManager;
    }

    public void setCptManager(CompteBancaireManager cptManager) {
        System.out.println("compteBancaireMBean.setCptManager(cptManager)");
        this.cptManager = cptManager;
    }

    public List<CompteBancaire> getCustList() {
        System.out.println("compteBancaireMBean.getCustList()");
        return custList;
    }

    public void setCustList(List<CompteBancaire> custList) {
        System.out.println("compteBancaireMBean.setCustList(custList)");
        this.custList = custList;
    }

    
    
    public void preRenderView() {
        System.out.println("compteBancaireMBean.preRenderView()");
        // Hack pour éviter l'erreur Cannot create a session after the response 
        // has been committed des datatables PrimeFaces

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        refreshListOfCptsFromDatabase();
    }


    public CompteBancaire getCompteBancaire() {
        System.out.println("compteBancaireMBean.getCompteBancaire()");
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        System.out.println("compteBancaireMBean.setCompteBancaire(compteBancaire)");
        this.compteBancaire = compteBancaire;
    }

 
    /*// Renvoie la liste des comptes pour affichage dans une DataTable
    //
    //@return
    public Collection getCompteBancaires() {
     System.out.println("DANS GET COMPTEBANCAIRES");
     return compteBancaireManager.getAllCompteBancaires();
    }
    /**/
     
    

    public List<CompteBancaire> getCompteBancaires() {
        System.out.println("compteBancaireMBean.getCompteBancaires()");
        System.out.println("DANS GET COMPTEBANCAIRE");
        if (custList == null) {
            refreshListOfCptsFromDatabase();
        }
        return custList;
    }

    // Renvoie les détails du compte courant (celui dans l'attribut compteBancaire de
    // cette classe), qu'on appelle une propriété (property)
    // @return
    public CompteBancaire getDetails() {
        System.out.println("compteBancaireMBean.getDetails()");
        return compteBancaire;
    }

    
     // Action handler - appelé lorsque l'utilisateur sélectionne une ligne dans
     // la DataTable pour voir les détails
     // @param compteBancaire
     // @return
    public String showDetails(CompteBancaire compteBancaire) {
        System.out.println("compteBancaireMBean.showDetails(compteBancaire)");
        this.compteBancaire = compteBancaire;
        return "compte-Details?faces-redirect=true";
    }

    /* 
    // Action handler - met à jour la base de données en fonction du compte
    // passé en paramètres
    // @return
    public String update() {
        System.out.println("compteBancaireMBean.update()");
        compteBancaire = cptManager.update(compteBancaire);
        return "comptes-List?faces-redirect=true";
    }
    /**/
   

     // Action handler - renvoie vers la page qui affiche la liste des comptes
     // @return
    public String list() {
        System.out.println("compteBancaireMBean.list()");
        //System.out.println("###LIST###");
        return "comptes-List?faces-redirect=true";
    }
 

    public void refreshListOfCptsFromDatabase() {
        System.out.println("compteBancaireMBean.refreshListOfCptsFromDatabase()");
        // true force le refresh depuis la base
        custList = cptManager.getAllCompteBancaires(true);

    }

    public void lireCptParId() {
        System.out.println("compteBancaireMBean.lireCptParId()");
        compteBancaire = cptManager.getCompteBancaireById(id);
    }
    
    
    // Action handler -  Créer un nouveau compte
    // @return String redirect vers la page list 
    public String createCpt() {
        System.out.println("compteBancaireMBean.createCpt()");
        //System.out.println("###CREATE###");
        // Creation du nouveau compte
        CompteBancaire newCpt = new CompteBancaire(this.nom, this.solde);
        cptManager.creerCompte(newCpt);
        // Rafraichir la liste des comptes pour inclure le nouveau compte
        this.refreshListOfCptsFromDatabase();
        
        // Vider les variables this;
        this.nom = "";
        this.solde = 0;
        //retour à la page list rafraichie
        return "comptes-List?faces-redirect=true";
    }
   //-----------------Claudia-----------------------------------------------------
    
    
  
    
    
    
}
