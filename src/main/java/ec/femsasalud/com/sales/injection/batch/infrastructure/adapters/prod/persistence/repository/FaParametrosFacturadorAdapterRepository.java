package ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.repository;

import ec.femsasalud.com.sales.injection.batch.domain.model.FaParametrosFacturador;
import ec.femsasalud.com.sales.injection.batch.domain.repository.FaParametrosFacturadorRepository;
import ec.femsasalud.com.sales.injection.batch.infrastructure.adapters.prod.persistence.mapper.FaParametrosFacturadorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FaParametrosFacturadorAdapterRepository implements FaParametrosFacturadorRepository {

    private final FaParametrosFacturadorMapper faParametrosFacturadorMapper;

    private final FaParametrosFacturadorJpaRepository faParametrosFacturadorJpaRepository;

    @Override
    public FaParametrosFacturador obtenerParametrosFacturador(String clave) {
        return faParametrosFacturadorMapper.toDomain(faParametrosFacturadorJpaRepository.findByClave(clave));
    }

    @Override
    public List<FaParametrosFacturador> obtenerParametrosFacturador(List<String> claves) {
        System.out.println("Claves: " + claves);
        return faParametrosFacturadorJpaRepository.findByClaveIn(claves).stream()
                .map(faParametrosFacturadorMapper::toDomain)
                .collect(Collectors.toList());
    }
}
