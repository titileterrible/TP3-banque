/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * 
 * @author thierry
 */
@Entity
public class OperationBancaire implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double montantOp;
    private String commentOp;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOp;

    
    // Constructeurs --------------------------------
    public OperationBancaire() { }

    public OperationBancaire(String commentOp, double montantOp) {
        this.dateOp = new Date();
        this.commentOp = commentOp;
        this.montantOp = montantOp;
        
    }
    
    // Getters et Setters --------------------------
    
    /**
     * Get the value of dateOp
     *
     * @return the value of dateOp
     */
    public Date getDateOp() {
        return dateOp;
    }
    
    
    /**
     * Get the formatted value of dateOp
     * 
     * @return 
     */
    public String getFormattedDateOp() {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String formattedDateOp = formatter.format(dateOp);     
        return formattedDateOp;
    }
    
    

    /**
     * Set the value of dateOp
     *
     * @param dateOp new value of dateOp
     */
    public void setDateOp(Date dateOp) {
        this.dateOp = dateOp;
    }


    /**
     * Get the value of commentOp
     *
     * @return the value of commentOp
     */
    public String getCommentOp() {
        return commentOp;
    }

    /**
     * Set the value of commentOp
     *
     * @param commentOp new value of commentOp
     */
    public void setCommentOp(String commentOp) {
        this.commentOp = commentOp;
    }

    

    /**
     * Get the value of montantOp
     *
     * @return the value of montantOp
     */
    public double getMontantOp() {
        return montantOp;
    }

    /**
     * Set the value of montantOp
     *
     * @param montantOp new value of montantOp
     */
    public void setMontantOp(double montantOp) {
        this.montantOp = montantOp;
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
        if (!(object instanceof OperationBancaire)) {
            return false;
        }
        OperationBancaire other = (OperationBancaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Opération n°" + id;
    }
    
}
