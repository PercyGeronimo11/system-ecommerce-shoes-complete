package com.hb.system.ecommerce.shoes.controllers;

import com.hb.system.ecommerce.shoes.dto.ApiResponse;
import com.hb.system.ecommerce.shoes.dto.request.LotRequest;
import com.hb.system.ecommerce.shoes.dto.response.LotCompleteResp;
import com.hb.system.ecommerce.shoes.dto.response.LotListResp;
import com.hb.system.ecommerce.shoes.entity.Lot;
import com.hb.system.ecommerce.shoes.services.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/lot")
public class LotController {
    @Autowired
    private LotService lotService;
    @GetMapping(
            value = {"/list"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<LotListResp>> index(@RequestParam(name = "search") String search){
        LotListResp lotListResp = lotService.lotListService(search);
        ApiResponse<LotListResp> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Lista de lotes exitosamente");
        response.setData(lotListResp);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(
            value = "/store",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Lot>> storeController(@RequestBody LotRequest lotRequest) throws IOException {
        Lot lot= lotService.lotStoreService(lotRequest);
        ApiResponse<Lot> response= new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Se a guardado el lote exitosamente");
        response.setData(lot);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<LotCompleteResp>> getById(@PathVariable int id){
        LotCompleteResp lot= lotService.lotGetService(id);
        ApiResponse<LotCompleteResp> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Detalle del lote recuperado exitossamente");
        response.setData(lot);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Lot>> editLot(@PathVariable int id, @RequestBody LotRequest lotEditReq){
        Lot lot= lotService.lotEditService(id,lotEditReq);
        ApiResponse<Lot> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("El loto ha sido editado exitosamente");
        response.setData(lot);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Value("${image.upload.directory}")
    private String uploadDir;

    @GetMapping("/images/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        Path imagePath = Paths.get(uploadDir).resolve(imageName);
        Resource imageResource;
        try {
            imageResource = new UrlResource(imagePath.toUri());
            if (imageResource.exists() || imageResource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}


