package com.degrize.hseapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.degrize.hseapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegleDTO.class);
        RegleDTO regleDTO1 = new RegleDTO();
        regleDTO1.setId(1L);
        RegleDTO regleDTO2 = new RegleDTO();
        assertThat(regleDTO1).isNotEqualTo(regleDTO2);
        regleDTO2.setId(regleDTO1.getId());
        assertThat(regleDTO1).isEqualTo(regleDTO2);
        regleDTO2.setId(2L);
        assertThat(regleDTO1).isNotEqualTo(regleDTO2);
        regleDTO1.setId(null);
        assertThat(regleDTO1).isNotEqualTo(regleDTO2);
    }
}
