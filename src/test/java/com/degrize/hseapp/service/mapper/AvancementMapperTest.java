package com.degrize.hseapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvancementMapperTest {

    private AvancementMapper avancementMapper;

    @BeforeEach
    public void setUp() {
        avancementMapper = new AvancementMapperImpl();
    }
}
