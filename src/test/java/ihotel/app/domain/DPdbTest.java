package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DPdbTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DPdb.class);
        DPdb dPdb1 = new DPdb();
        dPdb1.setId(1L);
        DPdb dPdb2 = new DPdb();
        dPdb2.setId(dPdb1.getId());
        assertThat(dPdb1).isEqualTo(dPdb2);
        dPdb2.setId(2L);
        assertThat(dPdb1).isNotEqualTo(dPdb2);
        dPdb1.setId(null);
        assertThat(dPdb1).isNotEqualTo(dPdb2);
    }
}
