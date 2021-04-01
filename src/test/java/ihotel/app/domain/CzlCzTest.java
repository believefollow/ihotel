package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CzlCzTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CzlCz.class);
        CzlCz czlCz1 = new CzlCz();
        czlCz1.setId(1L);
        CzlCz czlCz2 = new CzlCz();
        czlCz2.setId(czlCz1.getId());
        assertThat(czlCz1).isEqualTo(czlCz2);
        czlCz2.setId(2L);
        assertThat(czlCz1).isNotEqualTo(czlCz2);
        czlCz1.setId(null);
        assertThat(czlCz1).isNotEqualTo(czlCz2);
    }
}
