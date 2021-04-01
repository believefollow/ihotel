package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComsetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comset.class);
        Comset comset1 = new Comset();
        comset1.setId(1L);
        Comset comset2 = new Comset();
        comset2.setId(comset1.getId());
        assertThat(comset1).isEqualTo(comset2);
        comset2.setId(2L);
        assertThat(comset1).isNotEqualTo(comset2);
        comset1.setId(null);
        assertThat(comset1).isNotEqualTo(comset2);
    }
}
