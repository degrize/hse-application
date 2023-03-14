package com.degrize.hseapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.degrize.hseapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvancementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvancementDTO.class);
        AvancementDTO avancementDTO1 = new AvancementDTO();
        avancementDTO1.setId(1L);
        AvancementDTO avancementDTO2 = new AvancementDTO();
        assertThat(avancementDTO1).isNotEqualTo(avancementDTO2);
        avancementDTO2.setId(avancementDTO1.getId());
        assertThat(avancementDTO1).isEqualTo(avancementDTO2);
        avancementDTO2.setId(2L);
        assertThat(avancementDTO1).isNotEqualTo(avancementDTO2);
        avancementDTO1.setId(null);
        assertThat(avancementDTO1).isNotEqualTo(avancementDTO2);
    }
}
