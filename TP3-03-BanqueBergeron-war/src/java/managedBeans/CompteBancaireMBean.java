/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entite.CompteBancaire;
import entite.OperationBancaire;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.convert.Converter;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
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
    private CompteBancaire cptDebiteur;
    private CompteBancaire cptCrediteur;
    private int montantTransfert;
    private LazyDataModel<CompteBancaire> modele;

    public CompteBancaireMBean() {
        modele = new LazyDataModel<CompteBancaire>() {
            @Override
            public List load(int i, int i1, String string, SortOrder so, Map map) {
                return cptManager.getComptesByRange(i, i1);
            }

            @Override
            public int getRowCount() {
                return cptManager.getCountComptes();
            }
        };
    }
    
    public void setModele(LazyDataModel modele) {
        this.modele = modele;
    }

    public LazyDataModel getModele() {
        return modele;
    }

    /**
     * Crée une instance de CompteBancaireMBean
     */


    /**
     * Renvoie la liste des comptes pour affichage dans une DataTable
     *
     * @return
     */
    public Collection<CompteBancaire> getCompteBancaires() {
        System.out.println("CompteBancaireMBean.getCompteBancaires()");
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
     *
     * /**
     * Action handler - renvoie vers la page qui affiche la liste des comptes
     *
     * @return
     */
    public String list() {
        System.out.println("CompteBancaireMBean.list()");
        return "comptes-List?faces-redirect=true";
    }

    public void refreshListOfCptsFromDatabase() {
        System.out.println("CompteBancaireMBean.refreshListOfCptsFromDatabase()");
        // true force le refresh depuis la base
        custList = cptManager.getAllCompteBancaires(true);

    }

    public void lireCptParId() {
        System.out.println("CompteBancaireMBean.lireCptParId()");
        compteBancaire = cptManager.getCompteBancaireById(id);
    }

    /**
     * Action handler - Créer un nouveau compte
     *
     * @return String redirect vers la page list
     */
    public String createCpt() {
        System.out.println("CompteBancaireMBean.createCpt()");

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

    /**
     * Action handler - Créer une opération
     *
     * @return String redirect vers la page details
     */
    public String operation() {
        System.out.println("CompteBancaireMBean.operation()");

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
    //---------------virements------------------------------------

    public String transfert() {
        System.out.println("CompteBancaireMBean.transfert()");
        cptManager.transfert(cptDebiteur, cptCrediteur, montantTransfert);

        // Refresh 
        this.refreshListOfCptsFromDatabase();

        // Vider les variables
        cptDebiteur = null;
        cptCrediteur = null;
        montantTransfert = 0;

        addFlashMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Gestion des comptes", "Transfert effectué avec succès"));
        return "comptes-List?faces-redirect=true";
    }

    //---------------autocomplete pour la page virement-----------
    public List<CompteBancaire> autoCompleteCpt(String like) {
        return cptManager.findComptesLike(like);
    }
    // Autocomplete
    private Converter converter = new Converter() {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            return cptManager.getCompte(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            CompteBancaire c = (CompteBancaire) value;

            return c.getId().toString();
        }
    };

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }
    //---------------fin autocomplete pour la page virement----------
    private String txt6;

    public List<String> complete(String query) {
        List<String> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            results.add(query + i);
        }

        return results;
    }

    // ---- Fin transferts
    private void addFlashMessage(FacesMessage message) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);
        facesContext.addMessage(null, message);
    }

    //------------ Thierry ---------
    /*// DEBUGG >>> OK
     public Collection<OperationBancaire> getOperations() {
     System.out.println("compteBancaireMBean.getOperations()");
     Collection<OperationBancaire> operations = compteBancaire.getOperations();
     return operations;
     }
     /**/
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
        // Rafraichir la liste des comptes
        this.refreshListOfCptsFromDatabase();
        return "comptes-List?faces-redirect=true";
    }

    // -------------------- Getters et setters en vrac
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public CompteBancaire getCptDebiteur() {
        return cptDebiteur;
    }

    public void setCptDebiteur(CompteBancaire cptDebiteur) {
        this.cptDebiteur = cptDebiteur;
    }

    public CompteBancaire getCptCrediteur() {
        return cptCrediteur;
    }

    public void setCptCrediteur(CompteBancaire cptCrediteur) {
        this.cptCrediteur = cptCrediteur;
    }

    public int getMontantTransfert() {
        return montantTransfert;
    }

    public void setMontantTransfert(int montantTransfert) {
        this.montantTransfert = montantTransfert;
    }
}
