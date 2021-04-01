package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DUnit.class);
        DUnit dUnit1 = new DUnit();
        dUnit1.setId(1L);
        DUnit dUnit2 = new DUnit();
        dUnit2.setId(dUnit1.getId());
        assertThat(dUnit1).isEqualTo(dUnit2);
        dUnit2.setId(2L);
        assertThat(dUnit1).isNotEqualTo(dUnit2);
        dUnit1.setId(null);
        assertThat(dUnit1).isNotEqualTo(dUnit2);
    }
}
