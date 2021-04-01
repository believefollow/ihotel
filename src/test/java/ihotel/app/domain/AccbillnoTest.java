package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccbillnoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accbillno.class);
        Accbillno accbillno1 = new Accbillno();
        accbillno1.setId(1L);
        Accbillno accbillno2 = new Accbillno();
        accbillno2.setId(accbillno1.getId());
        assertThat(accbillno1).isEqualTo(accbillno2);
        accbillno2.setId(2L);
        assertThat(accbillno1).isNotEqualTo(accbillno2);
        accbillno1.setId(null);
        assertThat(accbillno1).isNotEqualTo(accbillno2);
    }
}
