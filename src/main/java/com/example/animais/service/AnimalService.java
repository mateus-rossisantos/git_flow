package com.example.animais.service;

import com.example.animais.dto.AnimalDTO;
import com.example.animais.model.Animal;
import com.example.animais.repository.AnimalRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Animal> save(AnimalDTO animalDto) {
        Animal animal = modelMapper.map(animalDto, Animal.class);
        animalRepository.save(animal);
        return new ResponseEntity<>(animal, HttpStatus.CREATED);
    }

    public Page<Animal> findAll(Pageable pageable) {
        return animalRepository.findAll(pageable);
    }


    public ResponseEntity<Animal> getOneAnimal(String id) {
        return animalRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Animal> replaceAnimal(AnimalDTO animalDto, String id) {
        Optional<Animal> animalOptional = animalRepository.findById(id);
        if (animalOptional.isPresent()){
            log.info("Animal encontrado.");
            Animal animal = modelMapper.map(animalDto, Animal.class);
            animal.setId(id);
            animalRepository.save(animal);
            return ResponseEntity.ok(animal);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> delete(String id) {
        Optional<Animal> animalOptional = animalRepository.findById(id);
        if (animalOptional.isPresent()) {
            animalRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
