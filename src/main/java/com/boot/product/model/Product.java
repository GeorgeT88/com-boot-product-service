package com.boot.product.model;

import com.boot.product.dto.ProductDTO;
import com.boot.product.enums.ProductStatus;

import com.boot.product.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Data
@Accessors(chain = true)
@Entity
@Table(name = "product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Product implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5714267227877816930L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true)
	@Size(min = 3, max = 30)
	private String name;

	@Column
	@Size(min = 3, max = 600)
	private String description;

	@Column
	private long price;

	@JsonManagedReference
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,  cascade = { CascadeType.ALL} )
	private List<PhotoLink> entries;

	@Column
	@Size(min = 3, max = 30)
	private String category;

	@Column
	private int stock;

	@Enumerated(EnumType.STRING)
	@Column
	private ProductStatus status;


	public static ProductDTO productEntityToDto(Product product) {
		return new ProductDTO()
				.setId(product.getId())
				.setName(product.getName())
				.setDescription(product.getDescription())
				.setPrice(product.getPrice())
				.setPhotoLinks(product.getEntries()!=null
						?product.getEntries().stream()
						.map(item->item.getPhotoLink())
						.collect(Collectors.toList())
						:List.of())
				.setCategory(product.getCategory())
				.setStock(product.getStock())
				.setStatus(product.getStatus());
	}

	public static Product dtoToProductEntity(ProductDTO productDto, List<PhotoLink> entries) {
		return new Product().setId(productDto.getId())
				.setName(productDto.getName())
				.setDescription(productDto.getDescription())
				.setPrice(productDto.getPrice())
				.setEntries(entries)
				.setCategory(productDto.getCategory())
				.setStock(productDto.getStock())
				.setStatus(productDto.getStatus());
	}

	public static Product updateDtoToProductEntity(Product product, ProductDTO productDto, List<PhotoLink> entries) {
		return product.setId(productDto.getId())
				.setName(productDto.getName())
				.setDescription(productDto.getDescription())
				.setPrice(productDto.getPrice())
				.setEntries(entries)
				.setCategory(productDto.getCategory())
				.setStock(productDto.getStock())
				.setStatus(productDto.getStatus());
	}


	public static List<ProductDTO> productEntityToDtoList(List<Product> productList) {

		List<ProductDTO> productDTOList = new ArrayList<>();

		productList.forEach(p -> productDTOList.add(productEntityToDto(p)));

		return productDTOList;
	}


}

