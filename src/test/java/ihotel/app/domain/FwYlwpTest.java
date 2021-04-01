package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FwYlwpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FwYlwp.class);
        FwYlwp fwYlwp1 = new FwYlwp();
        fwYlwp1.setId(1L);
        FwYlwp fwYlwp2 = new FwYlwp();
        fwYlwp2.setId(fwYlwp1.getId());
        assertThat(fwYlwp1).isEqualTo(fwYlwp2);
        fwYlwp2.setId(2L);
        assertThat(fwYlwp1).isNotEqualTo(fwYlwp2);
        fwYlwp1.setId(null);
        assertThat(fwYlwp1).isNotEqualTo(fwYlwp2);
    }
}
