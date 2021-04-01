package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FwJywpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FwJywp.class);
        FwJywp fwJywp1 = new FwJywp();
        fwJywp1.setId(1L);
        FwJywp fwJywp2 = new FwJywp();
        fwJywp2.setId(fwJywp1.getId());
        assertThat(fwJywp1).isEqualTo(fwJywp2);
        fwJywp2.setId(2L);
        assertThat(fwJywp1).isNotEqualTo(fwJywp2);
        fwJywp1.setId(null);
        assertThat(fwJywp1).isNotEqualTo(fwJywp2);
    }
}
