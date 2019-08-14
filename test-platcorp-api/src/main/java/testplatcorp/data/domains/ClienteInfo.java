package testplatcorp.data.domains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name="client_info")
@JsonPropertyOrder({"id", "ip", "dataCriacao", "tempAtual", "tempMinima", "tempMaxima"})
public class ClienteInfo implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "client_id", updatable = false, nullable = false)
	@JsonIgnore
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "create_dt", nullable = false)
	private Date dataCriacao;
	
	@Column(name = "ip_addr", length=15, nullable = false)
	private String ip;
	
	@Column(name = "now_temp", nullable = false)
	private Float tempAtual;
	
	@Column(name = "min_temp", nullable = false)
	private Float tempMinima;
	
	@Column(name = "max_temp", nullable = false)
	private Float tempMaxima;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Float getTempAtual() {
		return tempAtual;
	}
	public void setTempAtual(Float tempAtual) {
		this.tempAtual = tempAtual;
	}
	public Float getTempMinima() {
		return tempMinima;
	}
	public void setTempMinima(Float tempMinima) {
		this.tempMinima = tempMinima;
	}
	public Float getTempMaxima() {
		return tempMaxima;
	}
	public void setTempMaxima(Float tempMaxima) {
		this.tempMaxima = tempMaxima;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteInfo other = (ClienteInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
