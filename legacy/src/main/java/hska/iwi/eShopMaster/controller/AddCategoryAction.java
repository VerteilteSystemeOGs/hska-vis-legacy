package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.category.ApiException;
import hska.iwi.eShopMaster.integration.category.CategoryApiClientFactory;
import hska.iwi.eShopMaster.integration.category.api.CategoryApi;
import hska.iwi.eShopMaster.integration.category.api.CategoryDTO;
import hska.iwi.eShopMaster.integration.category.api.CreateNewCategoryRequestDTO;
import hska.iwi.eShopMaster.integration.user.UserApiClientFactory;
import hska.iwi.eShopMaster.integration.user.api.UserApi;
import hska.iwi.eShopMaster.integration.user.api.UserDTO;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6704600867133294378L;

	private final CategoryApi categoryApi = new CategoryApi(CategoryApiClientFactory.getClient());

	private final UserApi userApi = new UserApi(UserApiClientFactory.getClient());
	
	private String newCatName = null;

	private List<CategoryDTO> categories;
	
	UserDTO user;

	public String execute() throws Exception {

		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (UserDTO) session.get("webshop_user");

		boolean hasAdminRight = false;
		try {
			hasAdminRight = Boolean.TRUE.equals(userApi.hasUserAdminRight(user.getId()).getHasAdminRight());
		} catch (hska.iwi.eShopMaster.integration.user.ApiException ignored) {

		}

		if(hasAdminRight) {
			// Add category
			categoryApi.createNewCategory(new CreateNewCategoryRequestDTO().categoryName(newCatName));
			
			// Go and get new Category list
			this.setCategories(categoryApi.getAllCategories());
			
			res = "success";
		}
		
		return res;
	
	}
	
	@Override
	public void validate(){
		if (getNewCatName().length() == 0) {
			addActionError(getText("error.catname.required"));
		}
		// Go and get new Category list
		try {
			this.setCategories(categoryApi.getAllCategories());
		} catch (ApiException e) {
			this.setCategories(new ArrayList<>());
		}
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}
	
	public String getNewCatName() {
		return newCatName;
	}

	public void setNewCatName(String newCatName) {
		this.newCatName = newCatName;
	}
}
