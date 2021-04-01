package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FwWxfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FwWxf.class);
        FwWxf fwWxf1 = new FwWxf();
        fwWxf1.setId(1L);
        FwWxf fwWxf2 = new FwWxf();
        fwWxf2.setId(fwWxf1.getId());
        assertThat(fwWxf1).isEqualTo(fwWxf2);
        fwWxf2.setId(2L);
        assertThat(fwWxf1).isNotEqualTo(fwWxf2);
        fwWxf1.setId(null);
        assertThat(fwWxf1).isNotEqualTo(fwWxf2);
    }
}
