package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.category.ApiException;
import hska.iwi.eShopMaster.integration.category.CategoryApiClientFactory;
import hska.iwi.eShopMaster.integration.category.api.CategoryApi;
import hska.iwi.eShopMaster.integration.category.api.CategoryDTO;
import hska.iwi.eShopMaster.integration.category.api.CreateNewCategoryRequestDTO;
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
	
	private String newCatName = null;

	private List<CategoryDTO> categories;
	
	User user;

	public String execute() throws Exception {

		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("webshop_user");
		if(user != null && (user.getRole().getTyp().equals("admin"))) {
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
