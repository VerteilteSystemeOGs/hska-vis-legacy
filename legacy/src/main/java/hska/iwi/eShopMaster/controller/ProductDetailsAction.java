package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.product.ProductApiClientFactory;
import hska.iwi.eShopMaster.integration.product.api.ProductApi;
import hska.iwi.eShopMaster.integration.product.api.ProductDTO;
import hska.iwi.eShopMaster.integration.user.api.UserDTO;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetailsAction extends ActionSupport {
	
	private UserDTO user;
	private int id;
	private String searchValue;
	private Integer searchMinPrice;
	private Integer searchMaxPrice;
	private ProductDTO product;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7708747680872125699L;

	private final ProductApi productApi = new ProductApi(ProductApiClientFactory.getClient());

	public String execute() throws Exception {

		String res = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (UserDTO) session.get("webshop_user");
		
		if(user != null) {
			product = productApi.getProductById(id);
			
			res = "success";			
		}
		
		return res;		
	}
	
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Integer getSearchMinPrice() {
		return searchMinPrice;
	}

	public void setSearchMinPrice(Integer searchMinPrice) {
		this.searchMinPrice = searchMinPrice;
	}

	public Integer getSearchMaxPrice() {
		return searchMaxPrice;
	}

	public void setSearchMaxPrice(Integer searchMaxPrice) {
		this.searchMaxPrice = searchMaxPrice;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}
}
