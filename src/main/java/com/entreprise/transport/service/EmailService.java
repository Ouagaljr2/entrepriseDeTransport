package com.entreprise.transport.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.entreprise.transport.model.Trip;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {
    // Constructeur par défaut (vide ici)
    public EmailService() {
    }

    // Clé API SendGrid injectée via configuration (fichier application.properties)
    @Value("${sendgrid.api.key}")
    private String API_KEY;

    /**
     * Envoie un email au conducteur avec les détails d'un trajet.
     * Utilise l'API SendGrid pour envoyer un email.
     *
     * @param trip Le trajet contenant les informations à envoyer.
     * @throws IOException Si une erreur se produit lors de l'envoi de l'email.
     */
    public void sendEmail(Trip trip) throws IOException {
        // Création de l'email d'envoi depuis l'adresse configurée
        Email from = new Email("mhtouamht@gmail.com"); // Remplacer par ton adresse email SendGrid
        Email to = new Email(trip.getDriver().getEmail()); // L'email du conducteur à qui envoyer l'email

        // Affichage dans la console pour débogage
        System.out.println("Email: " + trip.getDriver().getEmail());
        System.out.println("drivername" + trip.getDriver().getName());

        // Sujet de l'email
        String subject = "Nouveau Trajet Assigné";

        // Corps de l'email formaté avec les détails du trajet
        String body = String.format(
                "Vous avez été assigné à un nouveau trajet. "
                        + "Détails :\n\nOrigine: %s\nDestination: %s\n Distance: %s\n Merci de vérifier votre disponibilité.",
                trip.getOrigin(), trip.getDestination(), trip.getDistance());

        // Contenu de l'email
        Content content = new Content("text/plain", body);

        // Création de l'objet Mail avec les informations nécessaires
        Mail mail = new Mail(from, subject, to, content);

        // Initialisation de l'objet SendGrid avec la clé API
        SendGrid sg = new SendGrid(API_KEY);

        // Création de la requête à envoyer à SendGrid
        Request request = new Request();
        request.setMethod(Method.POST); // Utilisation de la méthode POST pour envoyer l'email
        request.setEndpoint("mail/send"); // L'endpoint de l'API SendGrid pour l'envoi d'email
        request.setBody(mail.build()); // Corps de la requête avec les données de l'email

        try {
            // Envoi de la requête à l'API SendGrid
            sg.api(request);
            // Affichage de confirmation en cas de succès
            System.out.println("Email envoyé avec succès à " + to + "!");
        } catch (IOException ex) {
            // Gestion des erreurs lors de l'envoi
            System.err.println("Erreur lors de l'envoi de l'email : " + ex.getMessage());
        }
    }
}
