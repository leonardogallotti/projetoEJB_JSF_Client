package manager;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import session.ProdutoBeanRemote;
import entity.Produto;

@ManagedBean(name="mb")
@ViewScoped
public class ManagerBean implements Serializable {

	private static final long serialVersionUID = 1L;


	private Produto  produto;
	private List<Produto>  lista;
	private ProdutoBeanRemote<Produto> bean;
	
	
	public ManagerBean() {
		
	}
	
	@PostConstruct
	public void init(){
	 produto = new Produto();
	 try {
		Context  ctx = new InitialContext();
		bean = (ProdutoBeanRemote<Produto>) ctx.lookup("java:app/EJB_Client_JSF/ProdutoBean!session.ProdutoBeanRemote");
		 
	} catch (Exception e) {
		e.printStackTrace();
	}
	
  }
	
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getLista() {
		try {
			lista = bean.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void setLista(List<Produto> lista) {
		this.lista = lista;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public void gravar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			
			bean.create(produto);
			init();
			
			fc.addMessage("form1", new FacesMessage("Produto Gravados"));
		} catch (Exception e) {
			fc.addMessage("form1", new FacesMessage("error" + e.getMessage()));
		}
		
	}
	
	
	public void deletar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			
			
			bean.delete(produto.getIdProduto());
			
			init();
			fc.addMessage("form1", new FacesMessage("Produto Exluido"));
		} catch (Exception e) {
			fc.addMessage("form1", new FacesMessage("error" + e.getMessage()));
		}
		
	}
	
	public void atualizar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			bean.update(produto);
						
			fc.addMessage("form1", new FacesMessage("Produto Atualizado"));
		} catch (Exception e) {
			fc.addMessage("form1", new FacesMessage("error" + e.getMessage()));
		}
		
	}
	
	
	
	
	
	
}
