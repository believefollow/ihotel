package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DCktypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DCktype.class);
        DCktype dCktype1 = new DCktype();
        dCktype1.setId(1L);
        DCktype dCktype2 = new DCktype();
        dCktype2.setId(dCktype1.getId());
        assertThat(dCktype1).isEqualTo(dCktype2);
        dCktype2.setId(2L);
        assertThat(dCktype1).isNotEqualTo(dCktype2);
        dCktype1.setId(null);
        assertThat(dCktype1).isNotEqualTo(dCktype2);
    }
}
