package com.danielsilva.imcApplication.mapper;


import com.danielsilva.imcApplication.dto.ClienteDTO;
import com.danielsilva.imcApplication.model.ClienteModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface ClienteMapper {

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "altura", source = "altura")
    @Mapping(target = "peso", source = "peso")
    @Mapping(target = "imc", source = "imc")
    ClienteModel toDTO(ClienteDTO clienteDTO);

}
