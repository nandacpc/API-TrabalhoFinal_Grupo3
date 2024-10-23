package org.serratec.shablau.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender sender;

	public String enviarEmail(String destinatario, String assunto, String mensagem) {
		SimpleMailMessage enviaMensagem = new SimpleMailMessage();

		enviaMensagem.setFrom("nandacpc@gmail.com");
		enviaMensagem.setTo(destinatario);
		enviaMensagem.setSubject(assunto);
		enviaMensagem.setText(mensagem);

		try {
			sender.send(enviaMensagem);
			return "E-mail enviado com sucesso.";
		} catch (Exception e) {
			return "Erro ao enviar e-mail. Verifique.";
		}
	}
}
