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
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

class DisplayProductDTO extends ProductDTO {
	String categoryName;
	
	DisplayProductDTO(ProductDTO product, String categoryName) {
		super();

		this.setId(product.getId());
		this.setName(product.getName());
		this.setPrice(product.getPrice());
		this.setDetails(product.getDetails());
		this.setCategoryId(product.getCategoryId());
		this.categoryName = categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return this.categoryName;
	}
}

public class ListAllProductsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -94109228677381902L;
	
	private final ProductApi productApi = new ProductApi(ProductApiClientFactory.getClient());
	private final CategoryApi categoryApi = new CategoryApi(CategoryApiClientFactory.getClient());

	UserDTO user;
	private List<DisplayProductDTO> products = new ArrayList<DisplayProductDTO>();
	
	public String execute() throws Exception{
		String result = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (UserDTO) session.get("webshop_user");
		
		if(user != null){
			System.out.println("list all products!");
			List<ProductDTO> fetchProducts = productApi.getAllProducts();
			List<CategoryDTO> categories = categoryApi.getAllCategories();

			fetchProducts.forEach((product) -> {
				String categoryName = categories.stream().filter(category -> category.getId() == product.getCategoryId()).findFirst().map(resolvedCategory -> resolvedCategory.getName()).orElse("");

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

}
