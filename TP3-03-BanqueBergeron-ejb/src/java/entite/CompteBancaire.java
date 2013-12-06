/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author thierry
 */
@NamedQueries({
    @NamedQuery(
        name = "Cpt.findAll", query = "SELECT cpt FROM CompteBancaire cpt ORDER BY cpt.id"),
     @NamedQuery(
        name = "Cpt.delete", query = "delete from CompteBancaire cpt where cpt.id = :cptId"),
     @NamedQuery(
        name = "Cpt.CountCpts", query = "SELECT COUNT(cpt) FROM CompteBancaire cpt"),
     @NamedQuery(
        name = "Cpt.autoComplete", query = "select cpt from CompteBancaire cpt where lower(cpt.nom) like :search")
})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISC",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("CompteBancaire")
public class CompteBancaire implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private double solde;
    //private String typeCompte;


    
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    private List<OperationBancaire> operations = new ArrayList<>();
    /**/
    
    
    // Constructeurs -------------------------------
    public CompteBancaire() {
    }

    
    public CompteBancaire(String nom, double solde) {
    this.nom = nom;
    this.solde = solde;
    }

    
    // Méthodes ------------------------------------
    public void deposer(double montant) {
      this.solde += montant;
    }

    public double retirer(double montant) {
      //if (montant > solde) {
        this.solde -= montant;
        return solde;
//      } else {
//        return 0;
//      }
    }

    
    // Getters and setters -----------------------
    
    /**
     * Get the value of solde
     *
     * @return the value of solde
     */
    public double getSolde() {
        return solde;
    }

    /**
     * Set the value of solde
     *
     * @param solde new value of solde
     */
    public void setSolde(double solde) {
        this.solde = solde;
    }


    /**
     * Get the value of nom
     *
     * @return the value of nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the value of nom
     *
     * @param nom new value of nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompteBancaire)) {
            return false;
        }
        CompteBancaire other = (CompteBancaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Compte n°" + id + " ( " + nom + " ) ";
    }
    
    
    public List<OperationBancaire> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationBancaire> operations) {
        this.operations = operations;
    }

    
}
