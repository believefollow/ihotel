package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DayearndetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dayearndetail.class);
        Dayearndetail dayearndetail1 = new Dayearndetail();
        dayearndetail1.setId(1L);
        Dayearndetail dayearndetail2 = new Dayearndetail();
        dayearndetail2.setId(dayearndetail1.getId());
        assertThat(dayearndetail1).isEqualTo(dayearndetail2);
        dayearndetail2.setId(2L);
        assertThat(dayearndetail1).isNotEqualTo(dayearndetail2);
        dayearndetail1.setId(null);
        assertThat(dayearndetail1).isNotEqualTo(dayearndetail2);
    }
}
