package com.petition.api.helper;

import com.petition.api.dto.CreatePetitionDto;
import com.petition.model.petition.Petition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPetitionRequestMapper {
    Petition toModel(CreatePetitionDto createPetitionDto);
}

