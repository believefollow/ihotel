package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DDeptTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DDept.class);
        DDept dDept1 = new DDept();
        dDept1.setId(1L);
        DDept dDept2 = new DDept();
        dDept2.setId(dDept1.getId());
        assertThat(dDept1).isEqualTo(dDept2);
        dDept2.setId(2L);
        assertThat(dDept1).isNotEqualTo(dDept2);
        dDept1.setId(null);
        assertThat(dDept1).isNotEqualTo(dDept2);
    }
}
