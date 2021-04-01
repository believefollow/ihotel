package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FwEmpnTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FwEmpn.class);
        FwEmpn fwEmpn1 = new FwEmpn();
        fwEmpn1.setId(1L);
        FwEmpn fwEmpn2 = new FwEmpn();
        fwEmpn2.setId(fwEmpn1.getId());
        assertThat(fwEmpn1).isEqualTo(fwEmpn2);
        fwEmpn2.setId(2L);
        assertThat(fwEmpn1).isNotEqualTo(fwEmpn2);
        fwEmpn1.setId(null);
        assertThat(fwEmpn1).isNotEqualTo(fwEmpn2);
    }
}
