package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DDepotTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DDepot.class);
        DDepot dDepot1 = new DDepot();
        dDepot1.setId(1L);
        DDepot dDepot2 = new DDepot();
        dDepot2.setId(dDepot1.getId());
        assertThat(dDepot1).isEqualTo(dDepot2);
        dDepot2.setId(2L);
        assertThat(dDepot1).isNotEqualTo(dDepot2);
        dDepot1.setId(null);
        assertThat(dDepot1).isNotEqualTo(dDepot2);
    }
}
