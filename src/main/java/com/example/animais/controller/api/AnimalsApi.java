package com.example.animais.controller.api;


import com.example.animais.dto.AnimalDTO;
import com.example.animais.model.Animal;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RequestMapping("/animais")
@EnableSwagger2
public interface AnimalsApi {

    @Operation(summary = "Adiciona um animal",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "nome", required = true, schema = @Schema(implementation = String.class, example = "Rex"), description = "Nome do animal."),
                    @Parameter(in = ParameterIn.QUERY, name = "raça", required = true, schema = @Schema(implementation = String.class, example = "Cachorro"), description = "Raça do animal.")
            })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Insere um animal à lista"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    @Transactional
    ResponseEntity<Animal> postAnimal(@RequestBody AnimalDTO animaisDTO);

    @Operation(summary = "Lista os animais")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de animais"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Animal não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    Page<Animal> getAnimals(Pageable pageable);

    @Operation(summary = "Mostra um animal",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", required = true, schema = @Schema(implementation = String.class, example = "10"), description = "Id do animal.")
            })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um animal"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Animal não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    ResponseEntity<Animal> getAnimal(@PathVariable String id);

    @Operation(summary = "Altera um animal",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", required = true, schema = @Schema(implementation = String.class, example = "10"), description = "Id do animal."),
                    @Parameter(in = ParameterIn.QUERY, name = "nome", required = true, schema = @Schema(implementation = String.class, example = "rex"), description = "Nome do animal."),
                    @Parameter(in = ParameterIn.QUERY, name = "raça", required = true, schema = @Schema(implementation = String.class, example = "cachorro"), description = "raça do animal.")
            })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Altera um animal"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Animal não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("{id}")
    ResponseEntity<Animal> replaceAnimal(@RequestBody AnimalDTO animaisDto, @PathVariable String id);

    @Operation(summary = "Deleta um animal",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", required = true, schema = @Schema(implementation = String.class, example = "10"), description = "Id do anima.")
            })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Animal deletado com sucesso!"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Animal não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("{id}")
    @Transactional
    ResponseEntity<?> deleteAnimal(@PathVariable String id);

}
