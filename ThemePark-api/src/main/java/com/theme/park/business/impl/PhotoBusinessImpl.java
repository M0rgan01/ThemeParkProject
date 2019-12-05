package com.theme.park.business.impl;

import com.theme.park.business.ParkBusiness;
import com.theme.park.business.PhotoBusiness;
import com.theme.park.doa.PhotoRepository;
import com.theme.park.entities.Park;
import com.theme.park.entities.Photo;
import com.theme.park.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class PhotoBusinessImpl implements PhotoBusiness {

    private static final Logger logger = LoggerFactory.getLogger(PhotoBusinessImpl.class);
    private PhotoRepository photoRepository;
    private ParkBusiness parkBusiness;
    private String uploadLocation = "C:/Users/picha/Test/";

    public PhotoBusinessImpl(PhotoRepository photoRepository, ParkBusiness parkBusiness) {
        this.photoRepository = photoRepository;
        this.parkBusiness = parkBusiness;
    }

    @Override
    public Photo createPhoto(MultipartFile file, Long parkId) throws NotFoundException, IOException {
        Photo photo = new Photo();
        photo.setPark(parkBusiness.findById(parkId));

        photo.setName(file.getOriginalFilename());

        long millis = System.currentTimeMillis();
        String sig = Long.toHexString(millis);

        photo.setPath(uploadLocation + photo.getPark().getUrlName() + "/" + sig + photo.getName());

        File directory = new File(uploadLocation + photo.getPark().getUrlName());
        if (! directory.exists()){
            directory.mkdir();
            logger.info("directory created");
        }

        Files.write(Paths.get(photo.getPath()), file.getBytes());
        return photoRepository.save(photo);
    }

    @Override
    public Photo getPhotoById(Long id) throws NotFoundException {
        return photoRepository.findById(id).orElseThrow(() -> new NotFoundException("photo.id.incorrect"));
    }

    @Override
    public List<Photo> getPhotosByParkId(Long id) throws NotFoundException {
        Park park = parkBusiness.findById(id);
        return photoRepository.findByPark(park);
    }

    @Override
    public void deletePhoto(Long id) throws NotFoundException {
        Photo photo = getPhotoById(id);
        photoRepository.delete(photo);
        logger.info("Delete photo with id " + photo.getId());
    }
}
