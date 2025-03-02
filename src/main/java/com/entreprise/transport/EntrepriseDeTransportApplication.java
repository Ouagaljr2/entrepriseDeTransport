package com.entreprise.transport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de démarrage de l'application Mini TMS.
 * Cette classe contient la méthode `main()` qui lance l'application Spring Boot.
 * Elle est annotée avec `@SpringBootApplication`, ce qui inclut les fonctionnalités suivantes :
 * <ul>
 *   <li>Configuration automatique de Spring</li>
 *   <li>Scan des composants Spring dans le package courant</li>
 *   <li>Initialisation du serveur intégré (comme Tomcat)</li>
 * </ul>
 */
@SpringBootApplication
public class EntrepriseDeTransportApplication {

    /**
     * Méthode principale qui lance l'application Spring Boot.
     * 
     * @param args Les arguments passés à l'application lors de son lancement.
     */
    public static void main(String[] args) {
        SpringApplication.run(EntrepriseDeTransportApplication.class, args);
    }
}
