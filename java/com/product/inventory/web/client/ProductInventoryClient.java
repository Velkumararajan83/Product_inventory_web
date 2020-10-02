/**
 * 
 */
package com.product.inventory.web.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.product.inventory.web.dto.ProductInventoryClientDTO;
import com.product.inventory.web.dto.ProductInventoryDTO;
import com.product.inventory.web.exception.InvalidProductIdException;
import com.product.inventory.web.exception.ProductInventoryException;

/**
 * @author tvelk
 *
 */
@FeignClient(name = "inventory-application")
public interface ProductInventoryClient {
	
	@GetMapping(path = "/inventory/products",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<ProductInventoryClientDTO> getAllProductsFromInventory();
	
	@GetMapping(path = "/inventory/products/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ProductInventoryClientDTO getAProductFromInventory(@PathVariable String id) throws InvalidProductIdException;
	
	@PostMapping(path = "/inventory/products",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ProductInventoryClientDTO addAProductToInventory(@RequestBody ProductInventoryDTO productInventoryDTO) throws ProductInventoryException;
	
	@PutMapping(path = "/inventory/products",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ProductInventoryClientDTO updateProductToInventory(@RequestBody ProductInventoryDTO productInventoryDTO);
	
	@DeleteMapping(path = "/inventory/products/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> deleteAProductFromInventory(@PathVariable String id);
}
