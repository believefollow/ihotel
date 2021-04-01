package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CardysqTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cardysq.class);
        Cardysq cardysq1 = new Cardysq();
        cardysq1.setId(1L);
        Cardysq cardysq2 = new Cardysq();
        cardysq2.setId(cardysq1.getId());
        assertThat(cardysq1).isEqualTo(cardysq2);
        cardysq2.setId(2L);
        assertThat(cardysq1).isNotEqualTo(cardysq2);
        cardysq1.setId(null);
        assertThat(cardysq1).isNotEqualTo(cardysq2);
    }
}
