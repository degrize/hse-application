package com.degrize.hseapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SignalementMapperTest {

    private SignalementMapper signalementMapper;

    @BeforeEach
    public void setUp() {
        signalementMapper = new SignalementMapperImpl();
    }
}
