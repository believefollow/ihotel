package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DxSedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DxSed.class);
        DxSed dxSed1 = new DxSed();
        dxSed1.setId(1L);
        DxSed dxSed2 = new DxSed();
        dxSed2.setId(dxSed1.getId());
        assertThat(dxSed1).isEqualTo(dxSed2);
        dxSed2.setId(2L);
        assertThat(dxSed1).isNotEqualTo(dxSed2);
        dxSed1.setId(null);
        assertThat(dxSed1).isNotEqualTo(dxSed2);
    }
}
