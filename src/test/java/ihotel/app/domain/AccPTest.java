package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccPTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccP.class);
        AccP accP1 = new AccP();
        accP1.setId(1L);
        AccP accP2 = new AccP();
        accP2.setId(accP1.getId());
        assertThat(accP1).isEqualTo(accP2);
        accP2.setId(2L);
        assertThat(accP1).isNotEqualTo(accP2);
        accP1.setId(null);
        assertThat(accP1).isNotEqualTo(accP2);
    }
}
