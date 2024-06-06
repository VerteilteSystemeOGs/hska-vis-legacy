package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.product.ProductApiClientFactory;
import hska.iwi.eShopMaster.integration.product.api.ProductApi;
import hska.iwi.eShopMaster.integration.product.api.ProductDTO;
import hska.iwi.eShopMaster.integration.user.api.UserDTO;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ListAllProductsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -94109228677381902L;
	
	private final ProductApi productApi = new ProductApi(ProductApiClientFactory.getClient());

	UserDTO user;
	private List<ProductDTO> products;
	
	public String execute() throws Exception{
		String result = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (UserDTO) session.get("webshop_user");
		
		if(user != null){
			System.out.println("list all products!");
			this.products = productApi.getAllProducts();
			result = "success";
		}
		
		return result;
	}
	
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

}
