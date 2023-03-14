package com.degrize.hseapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.degrize.hseapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regle.class);
        Regle regle1 = new Regle();
        regle1.setId(1L);
        Regle regle2 = new Regle();
        regle2.setId(regle1.getId());
        assertThat(regle1).isEqualTo(regle2);
        regle2.setId(2L);
        assertThat(regle1).isNotEqualTo(regle2);
        regle1.setId(null);
        assertThat(regle1).isNotEqualTo(regle2);
    }
}
