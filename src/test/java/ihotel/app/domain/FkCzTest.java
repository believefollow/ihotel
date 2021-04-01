package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FkCzTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FkCz.class);
        FkCz fkCz1 = new FkCz();
        fkCz1.setId(1L);
        FkCz fkCz2 = new FkCz();
        fkCz2.setId(fkCz1.getId());
        assertThat(fkCz1).isEqualTo(fkCz2);
        fkCz2.setId(2L);
        assertThat(fkCz1).isNotEqualTo(fkCz2);
        fkCz1.setId(null);
        assertThat(fkCz1).isNotEqualTo(fkCz2);
    }
}
