/**
 * 
 */
package com.product.inventory.web.converter;

import java.util.ArrayList;
import java.util.List;

import com.product.inventory.web.dto.ProductInventoryClientDTO;
import com.product.inventory.web.dto.ProductInventoryDTO;

/**
 * @author tvelk
 *
 */
public class ProductInventoryWebConverter {

	public static List<ProductInventoryDTO> mapProductInventoryList(List<ProductInventoryClientDTO> clientList) {
		List<ProductInventoryDTO> productList = new ArrayList<ProductInventoryDTO>();
		
		for(ProductInventoryClientDTO clientDTO : clientList) {
			productList.add(mapProductInventoryDTO(clientDTO));
		}
		return productList;
	}
	public static ProductInventoryDTO mapProductInventoryDTO(ProductInventoryClientDTO clientDTO) {
		ProductInventoryDTO productDTO = new ProductInventoryDTO();
		productDTO.setAvailableQuantity(clientDTO.getAvailableQuantity());
		productDTO.setId(clientDTO.getId());
		productDTO.setPackageType(clientDTO.getPackageType());
		productDTO.setPrice(clientDTO.getPrice());
		productDTO.setProductName(clientDTO.getProductName());
		productDTO.setProductType(clientDTO.getProductType());
		productDTO.setProductVariety(clientDTO.getProductVariety());
		
		return productDTO;
	}
}