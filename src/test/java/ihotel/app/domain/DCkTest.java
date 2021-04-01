package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DCkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DCk.class);
        DCk dCk1 = new DCk();
        dCk1.setId(1L);
        DCk dCk2 = new DCk();
        dCk2.setId(dCk1.getId());
        assertThat(dCk1).isEqualTo(dCk2);
        dCk2.setId(2L);
        assertThat(dCk1).isNotEqualTo(dCk2);
        dCk1.setId(null);
        assertThat(dCk1).isNotEqualTo(dCk2);
    }
}
