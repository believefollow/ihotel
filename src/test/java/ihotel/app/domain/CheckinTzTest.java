package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckinTzTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckinTz.class);
        CheckinTz checkinTz1 = new CheckinTz();
        checkinTz1.setId(1L);
        CheckinTz checkinTz2 = new CheckinTz();
        checkinTz2.setId(checkinTz1.getId());
        assertThat(checkinTz1).isEqualTo(checkinTz2);
        checkinTz2.setId(2L);
        assertThat(checkinTz1).isNotEqualTo(checkinTz2);
        checkinTz1.setId(null);
        assertThat(checkinTz1).isNotEqualTo(checkinTz2);
    }
}
