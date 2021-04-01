package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DCktimeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DCktime.class);
        DCktime dCktime1 = new DCktime();
        dCktime1.setId(1L);
        DCktime dCktime2 = new DCktime();
        dCktime2.setId(dCktime1.getId());
        assertThat(dCktime1).isEqualTo(dCktime2);
        dCktime2.setId(2L);
        assertThat(dCktime1).isNotEqualTo(dCktime2);
        dCktime1.setId(null);
        assertThat(dCktime1).isNotEqualTo(dCktime2);
    }
}
