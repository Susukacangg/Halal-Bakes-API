package com.hbapi.repository;

import com.hbapi.entity.ProductInfoEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductInfoRepo extends JpaRepository<ProductInfoEntity, String> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM product_info
            WHERE barcode = :barcode
            """)
    Optional<ProductInfoEntity> findProductInfoByBarcode(@Param("barcode") String barcode);
}
