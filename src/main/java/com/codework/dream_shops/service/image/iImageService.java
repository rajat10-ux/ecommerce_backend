package com.codework.dream_shops.service.image;

import com.codework.dream_shops.DTO.ImageDTO;
import com.codework.dream_shops.Models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface iImageService {
Image getImageById(Long id);
void deleteImageById(Long id);
List<ImageDTO>saveImages(List<MultipartFile> file, Long ProductId);
void updateImage(MultipartFile file,Long imageId);
}
