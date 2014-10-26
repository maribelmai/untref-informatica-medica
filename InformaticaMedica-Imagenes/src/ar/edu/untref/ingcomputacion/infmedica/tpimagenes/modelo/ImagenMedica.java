package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ImagenMedica implements Serializable {
	
	private static final long serialVersionUID = 7547708445272941663L;
	
	private long id;
	private String imagenBase64;
	private String descripcion;
	private Estudio estudio;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public Estudio getEstudio() {
		return estudio;
	}
	public void setEstudio(Estudio estudio) {
		this.estudio = estudio;
	}
}
