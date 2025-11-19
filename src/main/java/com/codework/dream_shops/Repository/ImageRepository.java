package com.codework.dream_shops.Repository;

import com.codework.dream_shops.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {

    List<Image> findByProductId(Long id);
}
