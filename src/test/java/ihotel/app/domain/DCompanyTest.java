package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DCompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DCompany.class);
        DCompany dCompany1 = new DCompany();
        dCompany1.setId(1L);
        DCompany dCompany2 = new DCompany();
        dCompany2.setId(dCompany1.getId());
        assertThat(dCompany1).isEqualTo(dCompany2);
        dCompany2.setId(2L);
        assertThat(dCompany1).isNotEqualTo(dCompany2);
        dCompany1.setId(null);
        assertThat(dCompany1).isNotEqualTo(dCompany2);
    }
}
