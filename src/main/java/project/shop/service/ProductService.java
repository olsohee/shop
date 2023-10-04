package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.shop.dto.product.ProductRequest;
import project.shop.dto.product.ProductListResponse;
import project.shop.dto.product.ProductResponse;
import project.shop.dto.product.UpdateProductRequest;
import project.shop.entity.product.Product;
import project.shop.entity.product.ProductCategory;
import project.shop.entity.product.ProductImage;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Value("${file.dir}")
    private String fileDir;

    @Transactional
    public ProductResponse save(ProductRequest dto, List<MultipartFile> multipartFiles) throws IOException {

        // Product 생성
        Product product = Product.createProduct(dto.getName(), dto.getPrice(),
                dto.getStock(), ProductCategory.valueOf(dto.getProductCategory()));

        // 디렉터리에 파일 저장 후 ProductImage 생성
        List<ProductImage> productImages = createProductImages(multipartFiles);

        // Product - ProductImage 연관관계
        for (ProductImage productImage : productImages) {
            product.addProductImage(productImage);
        }

        // DB에 Product 저장
        productRepository.save(product);

        return ProductResponse.createResponse(product);
    }

    @Transactional
    public ProductResponse update(Long productId, UpdateProductRequest dto, List<MultipartFile> multipartFiles) throws IOException {

        // Product 조회
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

        // Product 수정
        product.updateProduct(dto.getName(), dto.getPrice(),
                dto.getStock(), ProductCategory.valueOf(dto.getProductCategory()));

        // 디렉터리에 파일 저장 후 ProductImage 생성
        List<ProductImage> newProductImages = createProductImages(multipartFiles);

        // Product - ProductImage 연관관계
        product.updateProductImages(newProductImages);

        // 응답
        return ProductResponse.createResponse(product);
    }

    public ProductResponse findById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
        return ProductResponse.createResponse(product);
    }

    public Page<ProductListResponse> findAll(Pageable pageable) {

        Page<Product> page = productRepository.findAll(pageable);
        return page.map(product -> ProductListResponse.createResponse(product));
    }

    public Page<ProductListResponse> findByCategory(String category, Pageable pageable) {

        Page<Product> page = productRepository.findByProductCategory(ProductCategory.valueOf(category.toUpperCase()), pageable);
        return page.map(product -> ProductListResponse.createResponse(product));
    }

    //== private 메서드 ==//
    private List<ProductImage> createProductImages(List<MultipartFile> multipartFiles) throws IOException {

        // 디렉터리에 파일 저장, ProductImage 생성
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                productImages.add(createProductImage(multipartFile));
            }
        }

        return productImages;
    }

    private ProductImage createProductImage(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty()) {
            return null;
        }

        // 디렉터리에 파일 저장
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        // ProductImage 생성 후 반환
        return ProductImage.createProductImage(originalFilename, storeFileName);
    }
    private String createStoreFileName(String originalFilename) {

        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {

        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    private String getFullPath(String storeFilename) {

        return fileDir + storeFilename;
    }
}
