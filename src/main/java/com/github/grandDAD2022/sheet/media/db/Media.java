package com.github.grandDAD2022.sheet.media.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Media {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PUBLICACION", nullable = false, unique = true)
	private long id;
	
	@Lob
	@JsonIgnore
	private byte[] mediaFile;
	
	@Column(name = "HASH")
	private String hash;
	
	// Véase https://www.baeldung.com/sha-256-hashing-java
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	/*
	public Media(Blob mediaFile) throws NoSuchAlgorithmException, IOException, SQLException {
		this.mediaFile = mediaFile;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		this.hash = bytesToHex(
				digest.digest(mediaFile.getBinaryStream().readAllBytes()));
	}
	*/

	public Media() {}
	
	public Media(byte[] bytes) throws NoSuchAlgorithmException {
		this.mediaFile = bytes;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		this.hash = bytesToHex(digest.digest(bytes));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getMediaFile() {
		return mediaFile;
	}

	/*
	public void setMediaFile(Blob mediaFile) throws NoSuchAlgorithmException, IOException, SQLException {
		this.mediaFile = mediaFile;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		this.hash = bytesToHex(
				digest.digest(mediaFile.getBinaryStream().readAllBytes()));
	}
	*/

	public void setMediaFile(byte[] bytes) throws NoSuchAlgorithmException {
		this.mediaFile = bytes;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		this.hash = bytesToHex(digest.digest(bytes));
	}

	@Override
	public String toString() {
		return "Media [id=" + id + ", hash=" + hash + "]";
	}
	
}