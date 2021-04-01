package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ee.class);
        Ee ee1 = new Ee();
        ee1.setId(1L);
        Ee ee2 = new Ee();
        ee2.setId(ee1.getId());
        assertThat(ee1).isEqualTo(ee2);
        ee2.setId(2L);
        assertThat(ee1).isNotEqualTo(ee2);
        ee1.setId(null);
        assertThat(ee1).isNotEqualTo(ee2);
    }
}
