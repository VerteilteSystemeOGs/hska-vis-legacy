package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.category.CategoryApiClientFactory;
import hska.iwi.eShopMaster.integration.category.api.CategoryApi;
import hska.iwi.eShopMaster.integration.category.api.CategoryDTO;
import hska.iwi.eShopMaster.integration.user.api.UserDTO;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class InitCategorySiteAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1108136421569378914L;

	private final CategoryApi categoryApi = new CategoryApi(CategoryApiClientFactory.getClient());

	private String pageToGoTo;
	private UserDTO user;

	private List<CategoryDTO> categories;

	public String execute() throws Exception {
		
		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (UserDTO) session.get("webshop_user");
		boolean isAdmin = true;
		if(user != null && isAdmin) {

			this.setCategories(categoryApi.getAllCategories());

			if(pageToGoTo != null){
				if(pageToGoTo.equals("c")){
					res = "successC";	
				}
				else if(pageToGoTo.equals("p")){
					res = "successP";
				}				
			}
		}
		
		return res;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}

	public String getPageToGoTo() {
		return pageToGoTo;
	}

	public void setPageToGoTo(String pageToGoTo) {
		this.pageToGoTo = pageToGoTo;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
