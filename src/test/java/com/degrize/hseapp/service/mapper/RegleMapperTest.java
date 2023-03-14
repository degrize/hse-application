package com.degrize.hseapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegleMapperTest {

    private RegleMapper regleMapper;

    @BeforeEach
    public void setUp() {
        regleMapper = new RegleMapperImpl();
    }
}
