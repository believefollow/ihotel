package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DType.class);
        DType dType1 = new DType();
        dType1.setId(1L);
        DType dType2 = new DType();
        dType2.setId(dType1.getId());
        assertThat(dType1).isEqualTo(dType2);
        dType2.setId(2L);
        assertThat(dType1).isNotEqualTo(dType2);
        dType1.setId(null);
        assertThat(dType1).isNotEqualTo(dType2);
    }
}
