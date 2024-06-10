package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.category.CategoryApiClientFactory;
import hska.iwi.eShopMaster.integration.category.api.CategoryApi;
import hska.iwi.eShopMaster.integration.category.api.CategoryDTO;
import hska.iwi.eShopMaster.integration.user.UserApiClientFactory;
import hska.iwi.eShopMaster.integration.user.api.UserApi;
import hska.iwi.eShopMaster.integration.user.api.UserDTO;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1254575994729199914L;

	private final CategoryApi categoryApi = new CategoryApi(CategoryApiClientFactory.getClient());

	private final UserApi userApi = new UserApi(UserApiClientFactory.getClient());

	private int catId;
	private List<CategoryDTO> categories;

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

			// Helper inserts new Category in DB:
			categoryApi.deleteCategoryById(catId);

			categories = categoryApi.getAllCategories();
				
			res = "success";

		}
		
		return res;
		
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}
}
