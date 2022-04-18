package com.github.grandDAD2022.sheet.media.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.grandDAD2022.sheet.media.db.Media;
import com.github.grandDAD2022.sheet.media.db.MediaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/")
@Tag(name = "Media", description = "API de object storage multimedia")
public class MediaController {
	
	@Autowired
	private MediaRepository media;
	
	@GetMapping("/")
	@Operation(summary = "Obtener lista de todos los archivos")
	public Collection<Media> getAllMedia() {
		return media.findAll();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtener archivo según id")
	public ResponseEntity<byte[]> getMedia(@PathVariable long id) throws SQLException {
		// TODO: Implementar múltiples tipos MIME
		Media m = media.findById(id).orElseThrow();
		if (m.getMediaFile() != null) {
			byte[] bytes = m.getMediaFile();
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").contentLength(bytes.length).body(bytes);
		}
		else 
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Subir un archivo")
	public Media uploadMedia(@RequestParam MultipartFile mediaFile) throws IOException, NoSuchAlgorithmException, SQLException {
		// Media m = new Media(BlobProxy.generateProxy(mediaFile.getInputStream(), mediaFile.getSize()));
		Media m = new Media(mediaFile.getBytes());
		media.save(m);
		return m;
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Destruir un archivo")
	public void deleteMedia(@PathVariable long id) {
		media.findById(id).orElseThrow();
		media.deleteById(id);
	}
	
	@DeleteMapping("/")
	@Operation(summary = "Destruir todos los archivos")
	public void deleteposts() {
		media.deleteAll();
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Actualizar un archivo")
	public Media updateFile(@PathVariable long id, @RequestParam MultipartFile mediaFile) throws IOException, NoSuchAlgorithmException, SQLException {
		Media m = media.findById(id).orElseThrow();
		// m.setMediaFile(BlobProxy.generateProxy(mediaFile.getInputStream(), mediaFile.getSize()));
		media.save(m);
		return m;
	}
 }