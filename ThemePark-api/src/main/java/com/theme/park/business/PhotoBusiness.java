package com.theme.park.business;

import com.theme.park.entities.Photo;
import com.theme.park.exception.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoBusiness {
    Photo createPhoto(MultipartFile file, Long parkId) throws NotFoundException, IOException;
    Photo getPhotoById(Long id) throws NotFoundException;
    List<Photo> getPhotosByParkId(Long id) throws NotFoundException;
    void deletePhoto(Long id) throws NotFoundException;
}
