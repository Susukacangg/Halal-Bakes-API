package com.hbapi.repository;

import com.hbapi.entity.ProductInfoIngredientEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoIngredientRepo extends JpaRepository<ProductInfoIngredientEntity, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM product_info_ingredient
            WHERE barcode_fk = :barcode
            """)
    List<ProductInfoIngredientEntity> findProductInfoIngredientByBarcode(@Param("barcode") String barcode);
}
