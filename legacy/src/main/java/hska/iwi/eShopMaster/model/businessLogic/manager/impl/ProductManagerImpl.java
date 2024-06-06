package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.integration.category.ApiException;
import hska.iwi.eShopMaster.integration.category.CategoryApiClientFactory;
import hska.iwi.eShopMaster.integration.category.api.CategoryApi;
import hska.iwi.eShopMaster.integration.category.api.CategoryDTO;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.ProductDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;

import java.util.List;

public class ProductManagerImpl implements ProductManager {
	private ProductDAO helper;

	// only temporary until product service is integrated
	// it will perform the category validation
	private final CategoryApi categoryApi = new CategoryApi(CategoryApiClientFactory.getClient());
	
	public ProductManagerImpl() {
		helper = new ProductDAO();
	}

	public List<Product> getProducts() {
		return helper.getObjectList();
	}
	
	public List<Product> getProductsForSearchValues(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice) {	
		return new ProductDAO().getProductListByCriteria(searchDescription, searchMinPrice, searchMaxPrice);
	}

	public Product getProductById(int id) {
		return helper.getObjectById(id);
	}

	public Product getProductByName(String name) {
		return helper.getObjectByName(name);
	}

	public int addProduct(String name, double price, int categoryId, String details) {
		int productId = -1;

		CategoryDTO categoryDTO = null;
		try {
			categoryDTO = categoryApi.getCategoryById(categoryId);
		} catch (ApiException e) {
			return productId;
		}

		if (categoryDTO != null) {
			Product product;
			// TODO this does not work because of transient entities. But this is removed when integrating the product service nonetheless
			Category category = new Category(categoryDTO.getName());
			if(details == null){
				product = new Product(name, price, category);	
			} else{
				product = new Product(name, price, category, details);
			}
			
			helper.saveObject(product);
			productId = product.getId();
		}
			 
		return productId;
	}
	

	public void deleteProductById(int id) {
		helper.deleteById(id);
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

}
