package com.shop.backend.repository;

import com.shop.backend.entity.Folder;
import com.shop.backend.entity.Product;
import com.shop.backend.entity.ProductFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductFolderRepository extends JpaRepository<ProductFolder, Long> {
    Optional<ProductFolder> findByProductAndFolder(Product product, Folder folder);
}