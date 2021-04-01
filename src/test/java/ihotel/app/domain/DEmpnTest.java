package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DEmpnTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DEmpn.class);
        DEmpn dEmpn1 = new DEmpn();
        dEmpn1.setId(1L);
        DEmpn dEmpn2 = new DEmpn();
        dEmpn2.setId(dEmpn1.getId());
        assertThat(dEmpn1).isEqualTo(dEmpn2);
        dEmpn2.setId(2L);
        assertThat(dEmpn1).isNotEqualTo(dEmpn2);
        dEmpn1.setId(null);
        assertThat(dEmpn1).isNotEqualTo(dEmpn2);
    }
}
