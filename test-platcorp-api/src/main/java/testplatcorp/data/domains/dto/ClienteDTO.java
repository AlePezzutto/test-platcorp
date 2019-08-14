package testplatcorp.data.domains.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import testplatcorp.data.domains.Cliente;

public class ClienteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer clienteId;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=100, message="O tamanho deve ser entre 5 e 100 caracteres")
	private String nome;
	
	@NotNull(message = "Informe a idade")
	@Min(18)
	@Max(99)
	private Short idade;

	public ClienteDTO() {
		super();
	}
	
	public ClienteDTO(Integer clienteId, String nome, Short idade) {
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

	public Cliente toCliente() {
		return new Cliente(this.clienteId, this.nome, this.idade);
	}
}
