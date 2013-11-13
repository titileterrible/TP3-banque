/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entite.CompteBancaire;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author thierry
 */
@Stateless
@LocalBean
public class CompteBancaireManager {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    @PersistenceContext
    private EntityManager em;
    
    
    // DAO de base --------------------------------
    
   
    public void persist(Object object) {
        em.persist(object);
    }

    public CompteBancaire update(CompteBancaire cpte) {
        return em.merge(cpte);
    }

    public CompteBancaire getCompte(long id) {
        return em.find(CompteBancaire.class, id);
    }

    public boolean deleteCompte(CompteBancaire cpte) {
        em.remove(em.merge(cpte));
        return true;
    }
    
    
    // DAO avancée ---------------------------------
    
    public List<CompteBancaire> getAllComptes() {
        Query query = em.createNamedQuery("Cpt.findAll");
        return query.getResultList();
    }
    
    
    public List<CompteBancaire> getComptesByRange(int offset, int qte) {
        Query query = em.createNamedQuery("Cpt.findAll");
        query.setMaxResults(qte);
        query.setFirstResult((offset - 1) * qte);
        return query.getResultList();
    }
    
    
    // Méthodes ------------------------------------

   public void creerComptesTest() {
        creerCompte(new CompteBancaire("John Lennon", 1500000));
        creerCompte(new CompteBancaire("Paul McCartney", 9500000));
        creerCompte(new CompteBancaire("Ringo Starr", 200000));
        creerCompte(new CompteBancaire("Georges Harrisson", 1000000));
        creerCompte(new CompteBancaire("Beattles Production inc", 3000000));
        creerCompte(new CompteBancaire("Michel Buffa", 2500));
        creerCompte(new CompteBancaire("Edouard Amosse", 1200));
        creerCompte(new CompteBancaire("Tresorerie Unice ", 100000));
        creerCompte(new CompteBancaire("John Lennon2", 1500000));
        creerCompte(new CompteBancaire("Paul McCartney2", 9500000));
        creerCompte(new CompteBancaire("Ringo Starr2", 200000));
        creerCompte(new CompteBancaire("Georges Harrisson2", 1000000));
        creerCompte(new CompteBancaire("Beattles Production inc2", 3000000));
        creerCompte(new CompteBancaire("Michel Buffa2", 2500));
        creerCompte(new CompteBancaire("Edouard Amosse2", 1200));
        creerCompte(new CompteBancaire("Tresorerie Unice2 ", 100000));
        creerCompte(new CompteBancaire("John Lennon3", 1500000));
        creerCompte(new CompteBancaire("Paul McCartney3", 9500000));
        creerCompte(new CompteBancaire("Ringo Starr3", 200000));
        creerCompte(new CompteBancaire("Georges Harrisson3", 1000000));
        creerCompte(new CompteBancaire("Beattles Production inc3", 3000000));
        creerCompte(new CompteBancaire("Michel Buffa3", 2500));
        creerCompte(new CompteBancaire("Edouard Amosse3", 1200));
        creerCompte(new CompteBancaire("Tresorerie Unice3 ", 100000));
     }

    public CompteBancaire creerCompte(CompteBancaire cpt) {
        em.persist(cpt);
        return cpt;
    } 
    

    
    public void depot(CompteBancaire cpt, double montant){
        cpt.deposer(montant);
        em.merge(cpt);
    }
    
    public void retrait(CompteBancaire cpt, double montant){
        cpt.retirer(montant);
        em.merge(cpt);
    }
    
    
    public void transfert(CompteBancaire cpt1, CompteBancaire cpt2, double montant){
        cpt1.retirer(montant);
        cpt2.deposer(montant);
        em.merge(cpt2);
        em.merge(cpt1);
    }
    
 

}
