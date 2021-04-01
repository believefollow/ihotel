package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DXsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DXs.class);
        DXs dXs1 = new DXs();
        dXs1.setId(1L);
        DXs dXs2 = new DXs();
        dXs2.setId(dXs1.getId());
        assertThat(dXs1).isEqualTo(dXs2);
        dXs2.setId(2L);
        assertThat(dXs1).isNotEqualTo(dXs2);
        dXs1.setId(null);
        assertThat(dXs1).isNotEqualTo(dXs2);
    }
}
