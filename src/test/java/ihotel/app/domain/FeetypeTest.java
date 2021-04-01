package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeetypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Feetype.class);
        Feetype feetype1 = new Feetype();
        feetype1.setId(1L);
        Feetype feetype2 = new Feetype();
        feetype2.setId(feetype1.getId());
        assertThat(feetype1).isEqualTo(feetype2);
        feetype2.setId(2L);
        assertThat(feetype1).isNotEqualTo(feetype2);
        feetype1.setId(null);
        assertThat(feetype1).isNotEqualTo(feetype2);
    }
}
