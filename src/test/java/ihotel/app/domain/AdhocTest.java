package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdhocTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adhoc.class);
        Adhoc adhoc1 = new Adhoc();
        adhoc1.setId("id1");
        Adhoc adhoc2 = new Adhoc();
        adhoc2.setId(adhoc1.getId());
        assertThat(adhoc1).isEqualTo(adhoc2);
        adhoc2.setId("id2");
        assertThat(adhoc1).isNotEqualTo(adhoc2);
        adhoc1.setId(null);
        assertThat(adhoc1).isNotEqualTo(adhoc2);
    }
}
