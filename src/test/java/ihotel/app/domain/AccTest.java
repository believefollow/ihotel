package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acc.class);
        Acc acc1 = new Acc();
        acc1.setId(1L);
        Acc acc2 = new Acc();
        acc2.setId(acc1.getId());
        assertThat(acc1).isEqualTo(acc2);
        acc2.setId(2L);
        assertThat(acc1).isNotEqualTo(acc2);
        acc1.setId(null);
        assertThat(acc1).isNotEqualTo(acc2);
    }
}
