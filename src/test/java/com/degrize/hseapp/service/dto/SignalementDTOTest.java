package com.degrize.hseapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.degrize.hseapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SignalementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SignalementDTO.class);
        SignalementDTO signalementDTO1 = new SignalementDTO();
        signalementDTO1.setId(1L);
        SignalementDTO signalementDTO2 = new SignalementDTO();
        assertThat(signalementDTO1).isNotEqualTo(signalementDTO2);
        signalementDTO2.setId(signalementDTO1.getId());
        assertThat(signalementDTO1).isEqualTo(signalementDTO2);
        signalementDTO2.setId(2L);
        assertThat(signalementDTO1).isNotEqualTo(signalementDTO2);
        signalementDTO1.setId(null);
        assertThat(signalementDTO1).isNotEqualTo(signalementDTO2);
    }
}
