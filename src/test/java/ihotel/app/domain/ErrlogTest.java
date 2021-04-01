package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ErrlogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Errlog.class);
        Errlog errlog1 = new Errlog();
        errlog1.setId(1L);
        Errlog errlog2 = new Errlog();
        errlog2.setId(errlog1.getId());
        assertThat(errlog1).isEqualTo(errlog2);
        errlog2.setId(2L);
        assertThat(errlog1).isNotEqualTo(errlog2);
        errlog1.setId(null);
        assertThat(errlog1).isNotEqualTo(errlog2);
    }
}
