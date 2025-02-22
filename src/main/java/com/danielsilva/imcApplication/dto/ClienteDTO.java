package com.danielsilva.imcApplication.dto;

import jakarta.validation.Valid;

public record ClienteDTO(@Valid String nome , Double imc , Double peso , Double altura){

}
