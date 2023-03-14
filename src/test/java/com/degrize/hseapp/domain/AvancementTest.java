package com.degrize.hseapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.degrize.hseapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvancementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avancement.class);
        Avancement avancement1 = new Avancement();
        avancement1.setId(1L);
        Avancement avancement2 = new Avancement();
        avancement2.setId(avancement1.getId());
        assertThat(avancement1).isEqualTo(avancement2);
        avancement2.setId(2L);
        assertThat(avancement1).isNotEqualTo(avancement2);
        avancement1.setId(null);
        assertThat(avancement1).isNotEqualTo(avancement2);
    }
}
