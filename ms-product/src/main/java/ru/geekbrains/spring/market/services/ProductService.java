package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.Constants;
import ru.geekbrains.spring.market.SortDirection;
import ru.geekbrains.spring.market.model.*;
import ru.geekbrains.spring.market.repositories.ProductCommentRepository;
import ru.geekbrains.spring.market.repositories.ProductRedisRepository;
import ru.geekbrains.spring.market.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRedisRepository productRedisRepository;

    @Autowired
    private ProductCommentRepository productCommentRepository;

    public Optional<FullProductDto> getById(Integer id) {
        Optional<FullProductDto> product = productRedisRepository.getProduct(id);
        if (product.isPresent()) {
            return product;
        }
        product = productRepository.findById(id).map(FullProductDto::new);
        product.ifPresent(p -> productRedisRepository.putProduct(p));
        return product;
    }

    public PageProductDto getAll(int page, int size) {
        PageProductDto pageProducts = productRedisRepository.getPage(page);
        if (pageProducts != null) {
            return pageProducts;
        }
        Page<ProductDto> productDtos = productRepository.findAll(PageRequest.of(page, size)).map(ProductDto::new);
        pageProducts = new PageProductDto(productDtos.getTotalPages(), page, productDtos.toList());
        productRedisRepository.putPage(page, pageProducts);
        return pageProducts;
    }

    public void updateCachePages() {
        productRedisRepository.deletePages();
        Page<ProductDto> page0 = productRepository.findAll(PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE)).map(ProductDto::new);
        int totalPages = page0.getTotalPages();
        productRedisRepository.putPage(0, new PageProductDto(page0.getTotalPages(), 0, page0.toList()));
        if (totalPages > 1) {
            for (int i = 1; i < totalPages; i++) {
                Page<ProductDto> pageCur = productRepository.findAll(PageRequest.of(i, Constants.DEFAULT_PAGE_SIZE)).map(ProductDto::new);
                productRedisRepository.putPage(i, new PageProductDto(pageCur.getTotalPages(), i, pageCur.toList()));
            }
        }
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

    public FullProductDto addOrUpdate(FullProductDto productDto) {
        FullProductDto product = new FullProductDto(productRepository.save(new Product(productDto)));
        updateCachePages();
        return product;
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
        productRedisRepository.deleteProduct(id);
        updateCachePages();
    }

    public void addProductComment(AddProductCommentDto comment) {
        Product product = productRepository.getOne(comment.getProductId());
        ProductComment productComment = new ProductComment(comment);
        productComment.setProduct(product);
        productCommentRepository.save(productComment);
    }

    public List<ProductBasketDto> getProductsByIds(List<Integer> listProductId) {
        return productRepository.findAllById(listProductId).stream().map(p -> {
            ProductBasketDto productBasketDto = new ProductBasketDto();
            productBasketDto.setId(p.getId());
            productBasketDto.setTitle(p.getTitle());
            productBasketDto.setPrice(p.getPrice());
            return productBasketDto;
        }).collect(Collectors.toList());
    }
}
