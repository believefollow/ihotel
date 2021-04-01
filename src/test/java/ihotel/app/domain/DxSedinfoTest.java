package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DxSedinfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DxSedinfo.class);
        DxSedinfo dxSedinfo1 = new DxSedinfo();
        dxSedinfo1.setId(1L);
        DxSedinfo dxSedinfo2 = new DxSedinfo();
        dxSedinfo2.setId(dxSedinfo1.getId());
        assertThat(dxSedinfo1).isEqualTo(dxSedinfo2);
        dxSedinfo2.setId(2L);
        assertThat(dxSedinfo1).isNotEqualTo(dxSedinfo2);
        dxSedinfo1.setId(null);
        assertThat(dxSedinfo1).isNotEqualTo(dxSedinfo2);
    }
}
