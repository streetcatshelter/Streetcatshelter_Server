package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.Cat;
import streetcatshelter.discatch.dto.CatRequestDto;
import streetcatshelter.discatch.service.CatService;

import java.awt.*;

@RequiredArgsConstructor
@RestController
public class CatController {

    private final CatService catService;

/*    @GetMapping("/cat")
    public Page<Cat> getCatByLocationPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "x", required = false) double x,
            @RequestParam(value = "y", required = false) double y
            ){
        return catService.getCatPage(page,size,x,y);
    }

    @PostMapping("/cat")
    public Cat createCat(@RequestBody CatRequestDto catRequestDto) {
        return catService.createCat(catRequestDto);
    }*/

}
