package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.domain.GenericEntity;
import com.lovotech.fr.gxld.cra.repositories.GenericRepository;
import com.lovotech.fr.gxld.cra.services.GenericService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public abstract class GenericController<T extends GenericEntity<T>> {

    private final GenericService<T> service;

    public GenericController(GenericRepository<T> repository) {
        this.service = new GenericService<T>(repository) {};
    }
    @GetMapping("")
    public ResponseEntity<PageDTO<T>> getPage(Pageable pageable, @RequestParam Map<String,String> inputs){
        inputs.remove("page");
        inputs.remove("size");
        return ResponseEntity.ok(this.service.getAllByFilter(inputs,pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<T> getOne(@PathVariable Long id){
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("")
    public ResponseEntity<T> update(@RequestBody T updated){
        return ResponseEntity.ok(service.update(updated));
    }

    @PostMapping("")
    public ResponseEntity<T> create(@RequestBody T created){
        return ResponseEntity.ok(service.create(created));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
