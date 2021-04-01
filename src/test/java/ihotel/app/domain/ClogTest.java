package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clog.class);
        Clog clog1 = new Clog();
        clog1.setId(1L);
        Clog clog2 = new Clog();
        clog2.setId(clog1.getId());
        assertThat(clog1).isEqualTo(clog2);
        clog2.setId(2L);
        assertThat(clog1).isNotEqualTo(clog2);
        clog1.setId(null);
        assertThat(clog1).isNotEqualTo(clog2);
    }
}
