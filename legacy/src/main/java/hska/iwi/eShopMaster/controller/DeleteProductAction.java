package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.product.ProductApiClientFactory;
import hska.iwi.eShopMaster.integration.product.api.ProductApi;
import hska.iwi.eShopMaster.integration.product.api.ProductDTO;

import hska.iwi.eShopMaster.integration.user.api.UserDTO;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteProductAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3666796923937616729L;

	private final ProductApi productApi = new ProductApi(ProductApiClientFactory.getClient());

	private int id;

	public String execute() throws Exception {
		
		String res = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("webshop_user");
		
		if(user != null && (user.getRole().getTyp().equals("admin"))) {

			productApi.deleteProductById(id);
			{
				res = "success";
			}
		}
		
		return res;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
