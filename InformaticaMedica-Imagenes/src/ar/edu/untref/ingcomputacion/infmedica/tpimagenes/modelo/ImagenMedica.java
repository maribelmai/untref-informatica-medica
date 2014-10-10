package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ImagenMedica {
	
	private long id;
	private String imagenBase64;
	private String descripcion;
	
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(length=10485760)
	public String getImagenBase64() {
		return imagenBase64;
	}
	public void setImagenBase64(String imagenBase64) {
		this.imagenBase64 = imagenBase64;
	}
	
	
	@Column
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
