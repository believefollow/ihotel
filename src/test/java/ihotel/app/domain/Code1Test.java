package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Code1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Code1.class);
        Code1 code11 = new Code1();
        code11.setId(1L);
        Code1 code12 = new Code1();
        code12.setId(code11.getId());
        assertThat(code11).isEqualTo(code12);
        code12.setId(2L);
        assertThat(code11).isNotEqualTo(code12);
        code11.setId(null);
        assertThat(code11).isNotEqualTo(code12);
    }
}
