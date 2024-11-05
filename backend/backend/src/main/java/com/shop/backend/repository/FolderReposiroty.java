package com.shop.backend.repository;

import com.shop.backend.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderReposiroty extends JpaRepository<Folder, Long> {

}