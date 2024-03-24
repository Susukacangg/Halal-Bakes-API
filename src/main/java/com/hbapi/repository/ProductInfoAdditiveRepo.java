package com.hbapi.repository;

import com.hbapi.entity.ProductInfoAdditive;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoAdditiveRepo extends JpaRepository<ProductInfoAdditive, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM product_info_additive
            WHERE barcode_fk = :barcode
            """)
    List<ProductInfoAdditive> findProductInfoAdditiveByBarcode(@Param("barcode") String barcode);
}
