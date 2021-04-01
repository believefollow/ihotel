package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckCzl2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckCzl2.class);
        CheckCzl2 checkCzl21 = new CheckCzl2();
        checkCzl21.setId(1L);
        CheckCzl2 checkCzl22 = new CheckCzl2();
        checkCzl22.setId(checkCzl21.getId());
        assertThat(checkCzl21).isEqualTo(checkCzl22);
        checkCzl22.setId(2L);
        assertThat(checkCzl21).isNotEqualTo(checkCzl22);
        checkCzl21.setId(null);
        assertThat(checkCzl21).isNotEqualTo(checkCzl22);
    }
}
