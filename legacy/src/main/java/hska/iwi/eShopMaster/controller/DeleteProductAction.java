package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.product.ProductApiClientFactory;
import hska.iwi.eShopMaster.integration.product.api.ProductApi;
import hska.iwi.eShopMaster.integration.product.api.ProductDTO;

import hska.iwi.eShopMaster.integration.user.UserApiClientFactory;
import hska.iwi.eShopMaster.integration.user.api.UserApi;
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

	private final UserApi userApi = new UserApi(UserApiClientFactory.getClient());

	private int id;

	public String execute() throws Exception {
		
		String res = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("webshop_user");

		boolean hasAdminRight = false;
		try {
			hasAdminRight = Boolean.TRUE.equals(userApi.hasUserAdminRight(user.getId()).getHasAdminRight());
		} catch (hska.iwi.eShopMaster.integration.user.ApiException ignored) {

		}

		if(hasAdminRight) {

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
