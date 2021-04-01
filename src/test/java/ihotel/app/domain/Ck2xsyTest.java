package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Ck2xsyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ck2xsy.class);
        Ck2xsy ck2xsy1 = new Ck2xsy();
        ck2xsy1.setId(1L);
        Ck2xsy ck2xsy2 = new Ck2xsy();
        ck2xsy2.setId(ck2xsy1.getId());
        assertThat(ck2xsy1).isEqualTo(ck2xsy2);
        ck2xsy2.setId(2L);
        assertThat(ck2xsy1).isNotEqualTo(ck2xsy2);
        ck2xsy1.setId(null);
        assertThat(ck2xsy1).isNotEqualTo(ck2xsy2);
    }
}
