package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DDbTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DDb.class);
        DDb dDb1 = new DDb();
        dDb1.setId(1L);
        DDb dDb2 = new DDb();
        dDb2.setId(dDb1.getId());
        assertThat(dDb1).isEqualTo(dDb2);
        dDb2.setId(2L);
        assertThat(dDb1).isNotEqualTo(dDb2);
        dDb1.setId(null);
        assertThat(dDb1).isNotEqualTo(dDb2);
    }
}
