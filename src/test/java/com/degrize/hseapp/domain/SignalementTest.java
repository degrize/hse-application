package com.degrize.hseapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.degrize.hseapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SignalementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Signalement.class);
        Signalement signalement1 = new Signalement();
        signalement1.setId(1L);
        Signalement signalement2 = new Signalement();
        signalement2.setId(signalement1.getId());
        assertThat(signalement1).isEqualTo(signalement2);
        signalement2.setId(2L);
        assertThat(signalement1).isNotEqualTo(signalement2);
        signalement1.setId(null);
        assertThat(signalement1).isNotEqualTo(signalement2);
    }
}
