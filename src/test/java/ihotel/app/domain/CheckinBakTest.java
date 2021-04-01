package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckinBakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckinBak.class);
        CheckinBak checkinBak1 = new CheckinBak();
        checkinBak1.setId(1L);
        CheckinBak checkinBak2 = new CheckinBak();
        checkinBak2.setId(checkinBak1.getId());
        assertThat(checkinBak1).isEqualTo(checkinBak2);
        checkinBak2.setId(2L);
        assertThat(checkinBak1).isNotEqualTo(checkinBak2);
        checkinBak1.setId(null);
        assertThat(checkinBak1).isNotEqualTo(checkinBak2);
    }
}
