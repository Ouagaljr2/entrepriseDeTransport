package com.entreprise.transport.service;

import com.entreprise.transport.model.Trip;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service

public class EmailService {

	private static final String  = "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";

	// private static final String  =
	// System.getenv("");

	public void sendEmail(Trip trip) throws IOException {		
		Email from = new Email("mhtouamht@gmail.com"); // Remplacer par ton adresse email SendGrid
		Email to = new Email(trip.getDriver().getEmail());

		System.out.println("Email: " + trip.getDriver().getEmail());
		System.out.println("drivername"+trip.getDriver().getName());
		
		String subject = "Nouveau Trajet Assigné";
		String body = String.format(
				"Vous avez été assigné à un nouveau trajet. "
				+ "Détails :\n\nOrigine: %s\nDestination: %s\n Distance: %s\n Merci de vérifier votre disponibilité.",
				trip.getOrigin(), trip.getDestination(),trip.getDistance());

		Content content = new Content("text/plain", body);
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid();
		Request request = new Request();
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		request.setBody(mail.build());

		try {
			sg.api(request);
			System.out.println("Email envoyé avec succès à " + to + "!");
		} catch (IOException ex) {
			System.err.println("Erreur lors de l'envoi de l'email : " + ex.getMessage());
		}
	}
}
