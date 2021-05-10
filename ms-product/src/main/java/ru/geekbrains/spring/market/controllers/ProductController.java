package ru.geekbrains.spring.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.Const;
import ru.geekbrains.spring.market.Constants;
import ru.geekbrains.spring.market.SortDirection;
import ru.geekbrains.spring.market.configurations.jwt.JwtProvider;
import ru.geekbrains.spring.market.exceptions.IncorrectParamException;
import ru.geekbrains.spring.market.exceptions.ProductNotFoundException;
import ru.geekbrains.spring.market.model.*;
import ru.geekbrains.spring.market.repositories.ProductSpecifications;
import ru.geekbrains.spring.market.services.ProductService;

import java.util.List;

@RestController
@RequestMapping
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping
    public List<ProductDto> getAll(@RequestParam MultiValueMap<String, String> params,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE_STR) Integer size,
                                   @RequestParam(required = false) String sortCost,
                                   @RequestParam(required = false) String sortTitle,
                                   @RequestParam(required = false) Boolean costFirst) {
        validatePagingParams(page, size);
        if (params.isEmpty() && sortCost == null && sortTitle == null &&
                costFirst == null && size.equals(Constants.DEFAULT_PAGE_SIZE)) {
            PageProductDto pageProductDto = productService.getAll(page - 1, size);
            if (page > pageProductDto.getCountOfPages()) {
                throw new IncorrectParamException("The total number of pages is " + pageProductDto.getCountOfPages());
            }
            return pageProductDto.getProducts();
        }
        validateSortingParams(sortCost, sortTitle, costFirst);
        try {
            Page<ProductDto> productPage = productService.getAll(ProductSpecifications.build(params), page - 1, size,
                    sortCost != null ? SortDirection.valueOf(sortCost) : null,
                    sortTitle != null ? SortDirection.valueOf(sortTitle) : null, costFirst);
            if (page > productPage.getTotalPages()) {
                throw new IncorrectParamException("The total number of pages is " + productPage.getTotalPages());
            }
            return productPage.toList();
        } catch (IllegalArgumentException e) {
            throw new IncorrectParamException(e.getMessage());
        }
    }

    public void validateSortingParams(String sortCost, String sortTitle, Boolean costFirst) {
        if (sortCost != null && sortTitle != null && costFirst == null) {
            throw new IncorrectParamException("Sort priority must be specified!");
        }
    }

    public void validatePagingParams(int page, int size) {
        if (page < 1) {
            throw new IncorrectParamException("Page index must not be less than one!");
        }
        if (size < 1) {
            throw new IncorrectParamException("Page size must not be less than one!");
        }
    }

    @GetMapping("/{id}")
    public FullProductDto getById(@PathVariable Integer id) {
        return productService.getById(id).orElseThrow(() -> new ProductNotFoundException("There is no product with id " + id));
    }

    @PostMapping("/by_ids")
    public List<ProductBasketDto> getProductsByIds(@RequestBody ProductBasketRequestDto productIds) {
        return productService.getProductsByIds(productIds.getListProductId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FullProductDto add(@RequestBody FullProductDto product) {
        product.setId(null);
        return productService.addOrUpdate(product);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public FullProductDto update(@RequestBody FullProductDto product) {
        return productService.addOrUpdate(product);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add_comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductComment(@RequestHeader(Const.AUTHORIZATION) String token, @RequestBody AddProductCommentDto comment) {
        comment.setUserId(jwtProvider.getUserIdFromToken(token.substring(7)));
        productService.addProductComment(comment);
    }
}
