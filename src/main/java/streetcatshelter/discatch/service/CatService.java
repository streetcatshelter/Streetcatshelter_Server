package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.Cat;
import streetcatshelter.discatch.domain.CatImage;
import streetcatshelter.discatch.domain.CatLocation;
import streetcatshelter.discatch.domain.CatTag;
import streetcatshelter.discatch.dto.CatRequestDto;
import streetcatshelter.discatch.repository.CatImageRepository;
import streetcatshelter.discatch.repository.CatLocationRepository;
import streetcatshelter.discatch.repository.CatRepository;
import streetcatshelter.discatch.repository.CatTagRepository;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final CatImageRepository catImageRepository;
    private final CatTagRepository catTagRepository;
    private final CatLocationRepository catLocationRepository;
/*
    public Page<Cat> getCatPage(int page, int size, double x, double y) {
        Pageable pageable = PageRequest.of(page, size);

        if(x ==0 || y==0) {
            return catRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        else {
            return catRepository.findAllByLocationAndOrderByCreatedAtDesc(pageable);
        }
    }*/

/*
    public Cat createCat(CatRequestDto catRequestDto) {
        Cat cat = new Cat(catRequestDto);
        catRepository.save(cat);
        CatImage catImage = new CatImage(catRequestDto);
        catImageRepository.save(catImage);
        CatLocation catLocation = new CatLocation(catRequestDto);
        catLocationRepository.save(catLocation);
        List<CatTag> catTags = new List<CatTag>(catRequestDto);
        catTagRepository.save(catTags);

        return cat;
    }
*/


}
