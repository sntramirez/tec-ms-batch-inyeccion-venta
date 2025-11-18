package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.web.controller;

import ec.femsasalud.com.sales.injection.batch.application.dto.request.VOFactura;
import ec.femsasalud.com.sales.injection.batch.application.dto.response.ApiResponseDTO;
import ec.femsasalud.com.sales.injection.batch.application.dto.response.VOResponse;
import ec.femsasalud.com.sales.injection.batch.application.usecase.FactureUseCase;
import ec.femsasalud.com.sales.injection.batch.domain.model.LogOrdenErrorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/facturador")
@RequiredArgsConstructor
public class FactureController {

    private final FactureUseCase factureUseCase;


    @PostMapping(
            path = "/venta",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponseDTO<List<LogOrdenErrorDTO>>> createFacture(@RequestBody VOFactura facture) {
        return new ResponseEntity<>( factureUseCase.processFacture(facture), HttpStatus.OK);
    }
}
