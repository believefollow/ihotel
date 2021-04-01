package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DKcTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DKc.class);
        DKc dKc1 = new DKc();
        dKc1.setId(1L);
        DKc dKc2 = new DKc();
        dKc2.setId(dKc1.getId());
        assertThat(dKc1).isEqualTo(dKc2);
        dKc2.setId(2L);
        assertThat(dKc1).isNotEqualTo(dKc2);
        dKc1.setId(null);
        assertThat(dKc1).isNotEqualTo(dKc2);
    }
}
