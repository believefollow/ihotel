package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccPpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccPp.class);
        AccPp accPp1 = new AccPp();
        accPp1.setId(1L);
        AccPp accPp2 = new AccPp();
        accPp2.setId(accPp1.getId());
        assertThat(accPp1).isEqualTo(accPp2);
        accPp2.setId(2L);
        assertThat(accPp1).isNotEqualTo(accPp2);
        accPp1.setId(null);
        assertThat(accPp1).isNotEqualTo(accPp2);
    }
}
