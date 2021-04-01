package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FwDsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FwDs.class);
        FwDs fwDs1 = new FwDs();
        fwDs1.setId(1L);
        FwDs fwDs2 = new FwDs();
        fwDs2.setId(fwDs1.getId());
        assertThat(fwDs1).isEqualTo(fwDs2);
        fwDs2.setId(2L);
        assertThat(fwDs1).isNotEqualTo(fwDs2);
        fwDs1.setId(null);
        assertThat(fwDs1).isNotEqualTo(fwDs2);
    }
}
