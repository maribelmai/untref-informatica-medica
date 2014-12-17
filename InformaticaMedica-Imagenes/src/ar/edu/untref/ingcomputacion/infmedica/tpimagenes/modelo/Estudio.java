package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Estudio implements Serializable {
	
	private static final long serialVersionUID = -4663366574758397460L;
	
	private long id;
	private Date fecha;
	private Paciente paciente;
	private String tipo;
	private int cantidadImagenes;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@Column
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Column
	public int getCantidadImagenes() {
		return cantidadImagenes;
	}
	public void setCantidadImagenes(int cantidadImagenes) {
		this.cantidadImagenes = cantidadImagenes;
	}
}
