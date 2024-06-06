package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.product.ProductApiClientFactory;
import hska.iwi.eShopMaster.integration.product.api.ProductApi;
import hska.iwi.eShopMaster.integration.product.api.ProductDTO;
import hska.iwi.eShopMaster.integration.product.api.CreateNewProductRequestDTO;

import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddProductAction extends ActionSupport {

	private static final long serialVersionUID = 39979991339088L;

	private final ProductApi productApi = new ProductApi(ProductApiClientFactory.getClient());

	private String name = null;
	private String price = null;
	private int categoryId = 0;
	private String details = null;

	public String execute() throws Exception {
		String result = "input";
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");

		if(user != null && (user.getRole().getTyp().equals("admin"))) {

			ProductDTO product = productApi.createNewProduct(new CreateNewProductRequestDTO().productName(name).categoryId(categoryId).price(Double.parseDouble(price)).details(details));

			if (product != null) {
				result = "success";
			}
		}

		return result;
	}

	@Override
	public void validate() {
		// Validate name:

		if (getName() == null || getName().length() == 0) {
			addActionError(getText("error.product.name.required"));
		}

		// Validate price:

		if (String.valueOf(getPrice()).length() > 0) {
			if (!getPrice().matches("[0-9]+(.[0-9][0-9]?)?")
					|| Double.parseDouble(getPrice()) < 0.0) {
				addActionError(getText("error.product.price.regex"));
			}
		} else {
			addActionError(getText("error.product.price.required"));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
