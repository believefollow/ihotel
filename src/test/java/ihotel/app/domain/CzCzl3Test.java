package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CzCzl3Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CzCzl3.class);
        CzCzl3 czCzl31 = new CzCzl3();
        czCzl31.setId(1L);
        CzCzl3 czCzl32 = new CzCzl3();
        czCzl32.setId(czCzl31.getId());
        assertThat(czCzl31).isEqualTo(czCzl32);
        czCzl32.setId(2L);
        assertThat(czCzl31).isNotEqualTo(czCzl32);
        czCzl31.setId(null);
        assertThat(czCzl31).isNotEqualTo(czCzl32);
    }
}
