/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import sessionBeans.CompteBancaireManager;

/**
 *
 * @author thierry
 */
@Singleton
@Startup
@LocalBean
public class InitDatabase {
    
    @EJB
    private CompteBancaireManager cptManager;

    @PostConstruct
    public void init() {
        System.out.println("### INIT BD ###");
        cptManager.creerComptesTest();
    }
}
