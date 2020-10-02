/**
 * 
 */
package com.product.inventory.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.product.inventory.web.client.ProductInventoryClient;
import com.product.inventory.web.converter.ProductInventoryWebConverter;
import com.product.inventory.web.dto.ProductInventoryDTO;
import com.product.inventory.web.exception.InvalidProductIdException;
import com.product.inventory.web.exception.ProductInventoryException;

/**
 * @author tvelk
 *
 */
@RestController
public class ProductInventoryWebController {
	@Autowired
	ProductInventoryClient productInventoryClient;
	
	@GetMapping(path = "/",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ModelAndView getDefaultPage() {
		ModelAndView model = new ModelAndView();
		
		model.setViewName("default");
		return model;
	}
	
	@GetMapping(path = "/products",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ModelAndView getAllProductsFromInventory() {
		ModelAndView model = new ModelAndView();
		model.addObject("productList", ProductInventoryWebConverter.mapProductInventoryList(productInventoryClient.getAllProductsFromInventory()));
		model.setViewName("product_summary");
		return model;
	}
	
	
	@GetMapping(path = "/products/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ProductInventoryDTO getAProductFromInventory(@PathVariable String id) {
	
		ProductInventoryDTO productDTO = null;
		try {
			 ProductInventoryWebConverter.mapProductInventoryDTO(productInventoryClient.getAProductFromInventory(id));
		}catch(InvalidProductIdException ex) {
			
		}
		
		return productDTO;
	}
	
	@GetMapping(path = "/addProduct")
	public ModelAndView addProduct() {
		ModelAndView model = new ModelAndView();
		model.addObject("product", new ProductInventoryDTO());
		model.setViewName("add_product");
		return model;
	}
	
	@PostMapping(path = "/saveProduct")
	public ModelAndView saveAProductToInventory(@ModelAttribute ProductInventoryDTO productInventoryDTO) {
		ModelAndView model = null;
		try {
			productInventoryClient.addAProductToInventory(productInventoryDTO);
		}catch(ProductInventoryException ex) {
			System.out.println("Exception caught");
			model = new ModelAndView();
			model.addObject("message", ex.getMessage());
			model.addObject("product", productInventoryDTO);
			model.setViewName("add_product");
		}
		if(model == null) {
			model = getAllProductsFromInventory();
			model.addObject("successMsg", "The product has been successfully added");
		}
		return model;
	}
	
	@GetMapping(path = "/editProduct/{id}")
	public ModelAndView editProduct(@PathVariable String id) {
		
		ModelAndView model = new ModelAndView();
		try {
			model.addObject("product", ProductInventoryWebConverter.mapProductInventoryDTO(productInventoryClient.getAProductFromInventory(id)));
		}catch(InvalidProductIdException ex) {
			
		}
		model.setViewName("update_product");
		
		return model;
	}
	
	
	@PostMapping(path = "/updateProduct")
	public ModelAndView updateProductToInventory(@ModelAttribute ProductInventoryDTO productInventoryDTO) {
		productInventoryClient.updateProductToInventory(productInventoryDTO);
		ModelAndView model = getAllProductsFromInventory();
		model.addObject("successMsg", "The details has been successfully updated");
		
		return model;
	}
	
	@GetMapping(path = "/deleteProduct/{id}")
	public ModelAndView deleteAProductFromInventory(@PathVariable String id) {
		ResponseEntity<String> response = productInventoryClient.deleteAProductFromInventory(id);
		ModelAndView model = getAllProductsFromInventory();
		model.addObject("successMsg", response.getBody());
		
		return model;
	}
}
