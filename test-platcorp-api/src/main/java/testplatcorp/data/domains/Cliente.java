package testplatcorp.data.domains;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name="client")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"clienteId", "nome", "idade", "clienteInfo"})
public class Cliente implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_id", updatable = false, nullable = false)
	@JsonProperty("client_id")
	private Integer clienteId;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	@Column(name = "client_name", length=100, nullable = false)
	private String nome;
	
	@NotNull(message = "Informe a idade")
	@Min(18)
	@Max(99)
	@Column(name = "client_age", length=1, nullable = false)
	private Short idade;
	
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private ClienteInfo clienteInfo;
	
	public Cliente() {
		super();
	}
	
	public Cliente(Integer clienteId, String nome, Short idade) {
		super();
		this.clienteId = clienteId;
		this.nome = nome;
		this.idade = idade;		
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Short getIdade() {
		return idade;
	}
	public void setIdade(Short idade) {
		this.idade = idade;
	}
	public ClienteInfo getClienteInfo() {
		return clienteInfo;
	}
	public void setClienteInfo(ClienteInfo clienteInfo) {
		this.clienteInfo = clienteInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((clienteId == null) ? 0 : clienteId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (clienteId == null) {
			if (other.clienteId != null)
				return false;
		} else if (!clienteId.equals(other.clienteId))
			return false;
		return true;
	}
	
	
}
