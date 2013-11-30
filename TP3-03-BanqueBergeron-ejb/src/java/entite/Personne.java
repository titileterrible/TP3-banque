/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author thierry
 */
//
public class Personne implements Serializable {
    
    private static final long serialVersionUID = 1L;
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomPersonne;
    private String prenomPersonne;
    

    //@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    //private List<CompteBancaire> comptes = new ArrayList<>();

    public Personne() {
    }
    
    
    public Personne(String nomPersonne, String prenomPersonne) {
        this.nomPersonne = nomPersonne;
        this.prenomPersonne = prenomPersonne;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPersonne() {
        return nomPersonne;
    }

    public void setNomPersonne(String nomPersonne) {
        this.nomPersonne = nomPersonne;
    }

    public String getPrenomPersonne() {
        return prenomPersonne;
    }

    public void setPrenomPersonne(String prenomPersonne) {
        this.prenomPersonne = prenomPersonne;
    }

    /*
    public List<CompteBancaire> getComptes() {
        return comptes;
    }

    public void setComptes(List<CompteBancaire> comptes) {
        this.comptes = comptes;
    }

    @Override
    public String toString() {
        return "Personne{" + "id=" + id + ", nomPersonne=" + nomPersonne + ", prenomPersonne=" + prenomPersonne + ", comptes=" + comptes + '}';
     
    }
    /**/




}
