package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DSpczTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DSpcz.class);
        DSpcz dSpcz1 = new DSpcz();
        dSpcz1.setId(1L);
        DSpcz dSpcz2 = new DSpcz();
        dSpcz2.setId(dSpcz1.getId());
        assertThat(dSpcz1).isEqualTo(dSpcz2);
        dSpcz2.setId(2L);
        assertThat(dSpcz1).isNotEqualTo(dSpcz2);
        dSpcz1.setId(null);
        assertThat(dSpcz1).isNotEqualTo(dSpcz2);
    }
}
