/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entite.CompteBancaire;
import entite.OperationBancaire;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;
import sessionBeans.CompteBancaireManager;




/**
 *
 * @author thierry
 */
@Named(value = "compteBancaireMBean")
@SessionScoped
public class CompteBancaireMBean implements Serializable {

    @EJB
    private CompteBancaireManager cptManager;
    //-----------------Claudia-----------------------------------------------------
    /* CompteBancaire courant dans la session, utilisé pour afficher ses détails ou
     * pour faire une mise à jour du compte modifié dans la base */
    private CompteBancaire compteBancaire;
    private int id;
    //-----------------Claudia-----------------------------------------------------
    private String nom;
    private int solde;
    private List<CompteBancaire> custList = null;
    //opération
    private String typeOperation;
    private int montantOp;
    private String commentOp;
    private CompteBancaire compteBancaireFrom;
    private CompteBancaire compteBancaireTo;
    private int montantTransfert;

    /**
     * Creates a new instance of CompteBancaireMBean
     */
    public CompteBancaireMBean() {
        System.out.println("BEAN CONSTRUIT !");
    }

//    public List<CompteBancaire> getAllCpts() {
//        return cptManager.getAllComptes();
//    }

    public List<CompteBancaire> getCptsByRange(int offset, int qte) {
        return cptManager.getAllComptes();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        System.out.println("#### DANS SET ID !!! ###");
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public int getMontantOp() {
        return montantOp;
    }

    public void setMontantOp(int montantOp) {
        this.montantOp = montantOp;
    }

    public String getCommentOp() {
        return commentOp;
    }

    public void setCommentOp(String commentOp) {
        this.commentOp = commentOp;
    }

    public CompteBancaireManager getCptManager() {
        return cptManager;
    }

    public void setCptManager(CompteBancaireManager cptManager) {
        this.cptManager = cptManager;
    }

    public List<CompteBancaire> getCustList() {
        return custList;
    }

    public void setCustList(List<CompteBancaire> custList) {
        this.custList = custList;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
    }

    public CompteBancaire getCompteBancaireFrom() {
        return compteBancaireFrom;
    }

    public void setCompteBancaireFrom(CompteBancaire compteBancaireFrom) {
        this.compteBancaireFrom = compteBancaireFrom;
    }

    public CompteBancaire getCompteBancaireTo() {
        return compteBancaireTo;
    }

    public void setCompteBancaireTo(CompteBancaire compteBancaireTo) {
        this.compteBancaireTo = compteBancaireTo;
    }

    public int getMontantTransfert() {
        return montantTransfert;
    }

    public void setMontantTransfert(int montantTransfert) {
        this.montantTransfert = montantTransfert;
    }

    /**
     * Renvoie la liste des comptes pour affichage dans une DataTable
     *
     * @return
     */
    /*
     public Collection getCompteBancaires() {
     System.out.println("DANS GET COMPTEBANCAIRES");
     return compteBancaireManager.getAllCompteBancaires();
     }
     */
    public List<CompteBancaire> getCompteBancaires() {

        System.out.println("DANS GET COMPTEBANCAIRE");
        if (custList == null) {
            refreshListOfCptsFromDatabase();
        }
        return custList;
    }

    /**
     * Renvoie les détails du compte courant (celui dans l'attribut
     * compteBancaire de cette classe), qu'on appelle une propriété (property)
     *
     * @return
     */
    public CompteBancaire getDetails() {
        return compteBancaire;
    }

    /**
     * Action handler - appelé lorsque l'utilisateur sélectionne une ligne dans
     * la DataTable pour voir les détails
     *
     * @param compteBancaire
     * @return
     */
    public String showDetails(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
        return "compte-Details?faces-redirect=true";
    }

    /**
     * Action handler - met à jour la base de données en fonction du compte
     * passé en paramètres
     *
     * @return
     */
    public String update() {
        compteBancaire = cptManager.update(compteBancaire);
        return "comptes-List?faces-redirect=true";
    }

    public String delete() {
        cptManager.delete(compteBancaire);
        return "comptes-List?faces-redirect=true";
    }

    /**
     * Action handler - renvoie vers la page qui affiche la liste des comptes
     *
     * @return
     */
    public String list() {
        System.out.println("###LIST###");
        return "comptes-List?faces-redirect=true";
    }

    public void refreshListOfCptsFromDatabase() {
        // true force le refresh depuis la base
        custList = cptManager.getAllCompteBancaires(true);

    }

    public void lireCptParId() {
        compteBancaire = cptManager.getCompteBancaireById(id);
    }

    /**
     * Action handler - Créer un nouveau compte
     *
     * @return String redirect vers la page list
     */
    public String createCpt() {
        System.out.println("###CREATE###");
        // Creation du nouveau compte
        CompteBancaire newCpt = new CompteBancaire(this.nom, this.solde);
        cptManager.creerCompte(newCpt);
        // Rafraichir la liste des comptes pour inclure le nouveau compte
        this.refreshListOfCptsFromDatabase();

        // Vider les variables this;
        this.nom = "";
        this.solde = 0;
        addFlashMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Gestion des comptes", "Compte créé avec succès"));
        return "comptes-List?faces-redirect=true";
    }

    public String operation() {
        System.out.println("###CREATE OPERATION###");
        // Creation d'une opération
        if (typeOperation.equals("retrait")) {
            cptManager.retrait(compteBancaire, montantOp);
        } else if (typeOperation.equals("depot")) {
            cptManager.depot(compteBancaire, montantOp);
        }

        compteBancaire = cptManager.update(compteBancaire);
        refreshListOfCptsFromDatabase();

        // Reinitialiser les variables this
        this.montantOp = 0;
        this.typeOperation = "";
        addFlashMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Gestion des comptes", "" + typeOperation + " Réussi"));
        return "compte-Details?faces-redirect=true";
    }

    public String transfert() {
        cptManager.transfert(compteBancaireFrom, compteBancaireTo, montantTransfert);
        System.out.println("###TRANSFERT OPERATION###");
        // Refresh 
        this.refreshListOfCptsFromDatabase();

        // Vider les variables
        compteBancaireFrom = null;
        compteBancaireFrom = null;
        montantTransfert = 0;

        addFlashMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Gestion des comptes", "Transfert effectué avec succès"));
        return "comptes-List?faces-redirect=true";
    }

    private void addFlashMessage(FacesMessage message) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);
        facesContext.addMessage(null, message);
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
   public void retrait0(){
       System.out.println("compteBancaireMBean.retrait0()");
       //
   } 
    
   // Methode vide... provisoire 
   public void depot0(){
       System.out.println("compteBancaireMBean.depot0()");
       //
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
    
     
    
   /**///-----------------Claudia --------------------------------------------------

















}
