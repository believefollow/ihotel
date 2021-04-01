package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckCzlTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckCzl.class);
        CheckCzl checkCzl1 = new CheckCzl();
        checkCzl1.setId(1L);
        CheckCzl checkCzl2 = new CheckCzl();
        checkCzl2.setId(checkCzl1.getId());
        assertThat(checkCzl1).isEqualTo(checkCzl2);
        checkCzl2.setId(2L);
        assertThat(checkCzl1).isNotEqualTo(checkCzl2);
        checkCzl1.setId(null);
        assertThat(checkCzl1).isNotEqualTo(checkCzl2);
    }
}
