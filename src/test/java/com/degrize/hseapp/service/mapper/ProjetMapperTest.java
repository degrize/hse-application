package com.degrize.hseapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjetMapperTest {

    private ProjetMapper projetMapper;

    @BeforeEach
    public void setUp() {
        projetMapper = new ProjetMapperImpl();
    }
}
