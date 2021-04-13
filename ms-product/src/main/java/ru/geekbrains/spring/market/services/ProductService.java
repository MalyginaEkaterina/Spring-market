package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.SortDirection;
import ru.geekbrains.spring.market.model.Product;
import ru.geekbrains.spring.market.model.ProductDto;
import ru.geekbrains.spring.market.repositories.ProductRepository;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<ProductDto> getById(Integer id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public Page<ProductDto> getAll(Specification<Product> specification, int page, int size,
                                   SortDirection sortCost, SortDirection sortTitle, Boolean costFirst) {
        Sort sortByCost = null;
        Sort sortByTitle = null;
        if (sortCost != null) {
            if (sortCost == SortDirection.ASC) {
                sortByCost = Sort.by("cost");
            } else {
                sortByCost = Sort.by("cost").descending();
            }
        }

        if (sortTitle != null) {
            if (sortTitle == SortDirection.ASC) {
                sortByTitle = Sort.by("title");
            } else {
                sortByTitle = Sort.by("title").descending();
            }
        }
        if (sortByCost == null && sortByTitle == null) {
            return productRepository.findAll(specification, PageRequest.of(page, size)).map(ProductDto::new);
        } else if (sortByCost == null) {
            return productRepository.findAll(specification, PageRequest.of(page, size, sortByTitle)).map(ProductDto::new);
        } else if (sortByTitle == null) {
            return productRepository.findAll(specification, PageRequest.of(page, size, sortByCost)).map(ProductDto::new);
        } else {
            if (costFirst) {
                return productRepository.findAll(specification, PageRequest.of(page, size, sortByCost.and(sortByTitle))).map(ProductDto::new);
            } else {
                return productRepository.findAll(specification, PageRequest.of(page, size, sortByTitle.and(sortByCost))).map(ProductDto::new);
            }
        }
    }

    public ProductDto addOrUpdate(ProductDto productDto) {
        return new ProductDto(productRepository.save(new Product(productDto)));
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
