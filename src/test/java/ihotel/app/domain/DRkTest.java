package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DRkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DRk.class);
        DRk dRk1 = new DRk();
        dRk1.setId(1L);
        DRk dRk2 = new DRk();
        dRk2.setId(dRk1.getId());
        assertThat(dRk1).isEqualTo(dRk2);
        dRk2.setId(2L);
        assertThat(dRk1).isNotEqualTo(dRk2);
        dRk1.setId(null);
        assertThat(dRk1).isNotEqualTo(dRk2);
    }
}
