package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CzBqzTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CzBqz.class);
        CzBqz czBqz1 = new CzBqz();
        czBqz1.setId(1L);
        CzBqz czBqz2 = new CzBqz();
        czBqz2.setId(czBqz1.getId());
        assertThat(czBqz1).isEqualTo(czBqz2);
        czBqz2.setId(2L);
        assertThat(czBqz1).isNotEqualTo(czBqz2);
        czBqz1.setId(null);
        assertThat(czBqz1).isNotEqualTo(czBqz2);
    }
}
