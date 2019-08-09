package br.com.alura.forum.model;

import java.util.Date;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenApp {
	
	private String issuer;
	private String subject;
	private Date dateCreation;	
	private SignatureAlgorithm signatureAlgorithm;	
	private String expiration;
	private String secret;	
	
	public TokenApp(String usuario, String expiration, String secret) {
		this.subject = usuario;
		this.expiration = expiration;
		this.secret = secret;
		this.issuer = "API do Fórum da Alura";
		this.subject = usuario;
		this.dateCreation = new Date();
		this.signatureAlgorithm = SignatureAlgorithm.HS256; //define o algoritmo de criptografia do token		
	}
	public String getExpiration() {
		return expiration;
	}	
	public String getSecret() {
		return secret;
	}	
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}	
	public SignatureAlgorithm getSignatureAlgorithm() {
		return signatureAlgorithm;
	}
	public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}
	
	public Date dateExpiration() {		
		return new Date(this.dateCreation.getTime() - Long.parseLong(this.getExpiration()));
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
}
