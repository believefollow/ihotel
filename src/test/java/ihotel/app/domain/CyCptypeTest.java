package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CyCptypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CyCptype.class);
        CyCptype cyCptype1 = new CyCptype();
        cyCptype1.setId(1L);
        CyCptype cyCptype2 = new CyCptype();
        cyCptype2.setId(cyCptype1.getId());
        assertThat(cyCptype1).isEqualTo(cyCptype2);
        cyCptype2.setId(2L);
        assertThat(cyCptype1).isNotEqualTo(cyCptype2);
        cyCptype1.setId(null);
        assertThat(cyCptype1).isNotEqualTo(cyCptype2);
    }
}
