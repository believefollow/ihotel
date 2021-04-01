package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrinfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Crinfo.class);
        Crinfo crinfo1 = new Crinfo();
        crinfo1.setId(1L);
        Crinfo crinfo2 = new Crinfo();
        crinfo2.setId(crinfo1.getId());
        assertThat(crinfo1).isEqualTo(crinfo2);
        crinfo2.setId(2L);
        assertThat(crinfo1).isNotEqualTo(crinfo2);
        crinfo1.setId(null);
        assertThat(crinfo1).isNotEqualTo(crinfo2);
    }
}
