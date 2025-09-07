package com.petition.api;

import com.petition.api.dto.CreatePetitionDto;
import com.petition.model.petition.PetitionSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/solicitud/",
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "savePetition",
                    operation = @Operation(
                            operationId = "savePetition",
                            summary = "Crear una solicitud",
                            tags = { "Solicitudes" },
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Datos de creación de la solicitud",
                                    content = @Content(
                                            schema = @Schema(implementation = CreatePetitionDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Solicitud creada correctamente",
                                            content = @Content(
                                                    schema = @Schema(implementation = CreatePetitionDto.class)
                                            )
                                    ),
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/solicitud/revision/findBySearch",
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "findBySearch",
                    operation = @Operation(
                            operationId = "findBySearch",
                            summary = "Buscar solicitudes por filtros",
                            description = "Permite buscar solicitudes aplicando filtros como estado, paginación, etc.",
                            tags = { "Solicitudes" },
                            parameters = {
                                    @Parameter(
                                            name = "stateId",
                                            in = ParameterIn.QUERY,
                                            description = "ID del estado de la solicitud",
                                            required = true,
                                            schema = @Schema(type = "integer", example = "1")
                                    ),
                                    @Parameter(
                                            name = "page",
                                            in = ParameterIn.QUERY,
                                            description = "Número de página (para paginación)",
                                            required = false,
                                            schema = @Schema(type = "integer", example = "0")
                                    ),
                                    @Parameter(
                                            name = "size",
                                            in = ParameterIn.QUERY,
                                            description = "Cantidad de registros por página",
                                            required = false,
                                            schema = @Schema(type = "integer", example = "10")
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Resultados encontrados",
                                            content = @Content(
                                                    array = @ArraySchema(schema = @Schema(implementation = PetitionSearchResponse.class))
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error en el servidor"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/solicitud/"), handler::savePetition)
                .andRoute(GET("/api/v1/solicitud/revision/findBySearch"), handler::findBySearch);
    }
}
