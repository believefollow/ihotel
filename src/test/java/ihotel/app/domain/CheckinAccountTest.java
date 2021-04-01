package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckinAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckinAccount.class);
        CheckinAccount checkinAccount1 = new CheckinAccount();
        checkinAccount1.setId(1L);
        CheckinAccount checkinAccount2 = new CheckinAccount();
        checkinAccount2.setId(checkinAccount1.getId());
        assertThat(checkinAccount1).isEqualTo(checkinAccount2);
        checkinAccount2.setId(2L);
        assertThat(checkinAccount1).isNotEqualTo(checkinAccount2);
        checkinAccount1.setId(null);
        assertThat(checkinAccount1).isNotEqualTo(checkinAccount2);
    }
}
