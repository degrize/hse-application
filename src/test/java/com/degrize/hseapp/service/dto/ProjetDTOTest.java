package com.degrize.hseapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.degrize.hseapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjetDTO.class);
        ProjetDTO projetDTO1 = new ProjetDTO();
        projetDTO1.setId(1L);
        ProjetDTO projetDTO2 = new ProjetDTO();
        assertThat(projetDTO1).isNotEqualTo(projetDTO2);
        projetDTO2.setId(projetDTO1.getId());
        assertThat(projetDTO1).isEqualTo(projetDTO2);
        projetDTO2.setId(2L);
        assertThat(projetDTO1).isNotEqualTo(projetDTO2);
        projetDTO1.setId(null);
        assertThat(projetDTO1).isNotEqualTo(projetDTO2);
    }
}
