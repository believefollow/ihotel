package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CzCzl2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CzCzl2.class);
        CzCzl2 czCzl21 = new CzCzl2();
        czCzl21.setId(1L);
        CzCzl2 czCzl22 = new CzCzl2();
        czCzl22.setId(czCzl21.getId());
        assertThat(czCzl21).isEqualTo(czCzl22);
        czCzl22.setId(2L);
        assertThat(czCzl21).isNotEqualTo(czCzl22);
        czCzl21.setId(null);
        assertThat(czCzl21).isNotEqualTo(czCzl22);
    }
}
