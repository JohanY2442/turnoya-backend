package com.turnoya.turnoyabackend.dto.triage;

import com.turnoya.turnoyabackend.entity.Sintoma;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TriageRequest(
        @NotEmpty @Size(max = 10) List<@NotNull Sintoma> sintomas
) {
}
