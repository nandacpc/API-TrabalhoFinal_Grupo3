package org.serratec.shablau.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender sender;	
	public String enviarEmail(String destinatario, String assunto, String mensagemHtml) {
	    try {
	        MimeMessage mimeMessage = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

	        helper.setText(mensagemHtml, true);
	        helper.setTo(destinatario);
	        helper.setSubject(assunto);
	        helper.setFrom("nandacpc@gmail.com");

	        sender.send(mimeMessage);
	        return "E-mail enviado com sucesso.";
	    } catch (Exception e) {
	        return "Erro ao enviar e-mail. Verifique.";
	    }

	}
}
