package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.category.CategoryApiClientFactory;
import hska.iwi.eShopMaster.integration.category.api.CategoryApi;
import hska.iwi.eShopMaster.integration.category.api.CategoryDTO;

import hska.iwi.eShopMaster.integration.product.ProductApiClientFactory;
import hska.iwi.eShopMaster.integration.product.api.ProductApi;
import hska.iwi.eShopMaster.integration.product.api.ProductDTO;

import hska.iwi.eShopMaster.integration.user.api.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6565401833074694229L;

	private final CategoryApi categoryApi = new CategoryApi(CategoryApiClientFactory.getClient());
	private final ProductApi productApi = new ProductApi(ProductApiClientFactory.getClient());

	private String searchDescription = null;
	private String searchMinPrice;
	private String searchMaxPrice;
	
	private Double sMinPrice = null;
	private Double sMaxPrice = null;
	
	private UserDTO user;
	private List<DisplayProductDTO> products = new ArrayList<DisplayProductDTO>();
	private List<CategoryDTO> categories;
	

	public String execute() throws Exception {
		
		String result = "input";
		
		// Get user:
		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (UserDTO) session.get("webshop_user");
		ActionContext.getContext().setLocale(Locale.US);  
		
		if(user != null){
			// Search products and show results:
			if (!searchMinPrice.isEmpty()){
				sMinPrice =  Double.parseDouble(this.searchMinPrice);
			}
			if (!searchMaxPrice.isEmpty()){
				sMaxPrice =  Double.parseDouble(this.searchMaxPrice);
			}
			List<ProductDTO> fetchProducts = productApi.filterProducts(sMinPrice, sMaxPrice, this.searchDescription);
			this.categories = categoryApi.getAllCategories();

			fetchProducts.forEach((product) -> {
				String categoryName = this.categories.stream().filter(category -> category.getId() == product.getCategoryId()).findFirst().map(resolvedCategory -> resolvedCategory.getName()).orElse("");

				DisplayProductDTO newProduct = new DisplayProductDTO(product, categoryName);
				this.products.add(newProduct);
			});
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
		
		public List<DisplayProductDTO> getProducts() {
			return products;
		}

		public void setProducts(List<DisplayProductDTO> products) {
			this.products = products;
		}

	public List<CategoryDTO> getCategories() {
			return categories;
		}

	public void setCategories(List<CategoryDTO> categories) {
			this.categories = categories;
		}
		
		


	public String getSearchValue() {
		return searchDescription;
	}


	public void setSearchValue(String searchValue) {
		this.searchDescription = searchValue;
	}


	public String getSearchMinPrice() {
		return searchMinPrice;
	}


	public void setSearchMinPrice(String searchMinPrice) {
		this.searchMinPrice = searchMinPrice;
	}


	public String getSearchMaxPrice() {
		return searchMaxPrice;
	}


	public void setSearchMaxPrice(String searchMaxPrice) {
		this.searchMaxPrice = searchMaxPrice;
	}


//	public Double getSearchMinPrice() {
//		return searchMinPrice;
//	}
//
//
//	public void setSearchMinPrice(Double searchMinPrice) {
//		this.searchMinPrice = searchMinPrice;
//	}
//
//
//	public Double getSearchMaxPrice() {
//		return searchMaxPrice;
//	}
//
//
//	public void setSearchMaxPrice(Double searchMaxPrice) {
//		this.searchMaxPrice = searchMaxPrice;
//	}
}
