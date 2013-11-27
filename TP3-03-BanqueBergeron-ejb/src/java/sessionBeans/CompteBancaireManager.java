/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entite.CompteBancaire;
import entite.OperationBancaire;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author GLYSE581B
 */
@Stateless
@LocalBean
public class CompteBancaireManager {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;
   // @Resource
    //UserTransaction utx;

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

    public void delete(CompteBancaire cpte) {
        em.remove(em.merge(cpte));
        em.createQuery("delete from CompteBancaire c where c.id=" + cpte.getId()).executeUpdate();

    }

    // DAO avancée ---------------------------------
    public List<CompteBancaire> getAllComptes() {
        Query query = em.createNamedQuery("Cpt.findAll");
        return query.getResultList();
    }
  
        public List<CompteBancaire> getComptesByRange(int start, int nbComptes){
        
        Query query =em.createNamedQuery("Cpt.findAll");
        query.setFirstResult(start);
        query.setMaxResults(nbComptes);
        
        return query.getResultList();
    }

    public CompteBancaire creerCompte(CompteBancaire cpt) {
        em.persist(cpt);
        // Enregistrement de l'opération de création
        OperationBancaire op = new OperationBancaire("Création du compte " + cpt.getNom(), cpt.getSolde());
        cpt.getOperations().add(op);
        
        return cpt;
    }

    public void depot(CompteBancaire cpt, double montant) {
        cpt.deposer(montant);
        OperationBancaire op = new OperationBancaire("Dépot vers le compte " + cpt.getNom(), montant);
        cpt.getOperations().add(op);
        em.merge(cpt);
    }

    public void retrait(CompteBancaire cpt, double montant) {
        cpt.retirer(montant);
        OperationBancaire op = new OperationBancaire("Retrait à partir du compte " + cpt.getNom(), montant);
        cpt.getOperations().add(op);
        em.merge(cpt);
    }

    public void transfert(CompteBancaire cpt1, CompteBancaire cpt2, double montant) {
        System.out.println("CompteBancaireManager.transfert()");
        System.out.println("#### TRANSFERT ###" + cpt1 + "   " + cpt2 + "   " + montant);
        try {
            //this.retrait(cpt1, montant);
            cpt1.retirer(montant);
            OperationBancaire op = new OperationBancaire("Virement vers le compte " + cpt2.getNom(), montant);
            cpt1.getOperations().add(op);
            em.merge(cpt1);
        } catch (Exception e) {
            // Si une erreur est apparue, on arrête.
            return;
        }
        try {
            //this.depot(cpt2, montant);
            cpt2.deposer(montant);
            OperationBancaire op = new OperationBancaire("Virement reçu du compte " + cpt1.getNom(), montant);
            cpt2.getOperations().add(op);
            em.merge(cpt2);


        } catch (Exception e) {
            //Si une erreur est apparue, o s'arrête, mais avant, on redépose 
            //l'argent sur le compte 1
            this.depot(cpt1, montant);
            //cpt1.getOperations().remove(cpt1.getOperations().size() - 1);

            return;
        }

    }
//    public void transfert(CompteBancaire cpt1, CompteBancaire cpt2, double montant) {
//        try {
//            utx.begin();
//            if (cpt1.getSolde() < montant) {
//                 Logger.getLogger(CompteBancaireManager.class.getName()).log(Level.INFO, "Votre solde ne permet pas de faire un virement ");
//                utx.rollback();
//            }
//            this.retrait(cpt1, montant);
//            this.depot(cpt2, montant);
//            utx.commit();
//        } catch (NotSupportedException | SystemException | IllegalStateException | SecurityException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
//            try {
//                utx.rollback();
//            } catch (    IllegalStateException | SecurityException | SystemException ex) {
//                Logger.getLogger(CompteBancaireManager.class.getName()).log(Level.SEVERE, "Erreur lors de transfert "+ex.getMessage(), ex);
//            }
//            Logger.getLogger(CompteBancaireManager.class.getName()).log(Level.SEVERE, "Erreur lors de transfert "+e.getMessage(), e);
//        }
//    }

    // Recherche de compte 
    public List<CompteBancaire> findComptesLike(String search) {
        Query query = em.createNamedQuery("Cpt.autoComplete");
        query.setParameter("search", "%" + search + "%");
        return query.getResultList();
    }

    // Liste de compte avec option refresh ------------------------------------
    public List<CompteBancaire> getAllCompteBancaires(boolean forceRefresh) {
        Query query = em.createNamedQuery("Cpt.findAll");
        // Cette liste provient du cache de niveau 2 et 1
        // Si les données changent en insert/delete, la liste est à jour
        // Mais pas forcément les updates
        List<CompteBancaire> liste = query.getResultList();

        // Force le refresh des valeurs
        if (forceRefresh) {
            for (CompteBancaire compteBancaire : liste) {
                // em.refresh force le rafraichissement des
                // attributs de l'objet en mémoire en fonction
                // des dernières valeurs pour cet objet, dans la base
                // (au plus près du dernier commit)
                em.refresh(compteBancaire);
            }
        }

        return liste;
    }

    public int getCountComptes() {
        Query query = em.createNamedQuery("Cpt.CountCpts");
return ((Long) query.getSingleResult()).intValue();
    }

    public void creerOperation(CompteBancaire compteBancaire, String descrption, float montant) {
        OperationBancaire op = new OperationBancaire(descrption, montant);
        compteBancaire.getOperations().add(op);
    }

    public CompteBancaire consulter(long id) {
        return em.find(CompteBancaire.class, id);
    }

    public CompteBancaire getCompteBancaireById(int id) {

        System.out.println("#### JE VAIS CHERCHER LE COMPTE DANS LA BASE ###");
        return em.find(CompteBancaire.class, id);
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
}
