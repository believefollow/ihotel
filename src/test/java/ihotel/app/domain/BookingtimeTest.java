package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookingtimeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bookingtime.class);
        Bookingtime bookingtime1 = new Bookingtime();
        bookingtime1.setId(1L);
        Bookingtime bookingtime2 = new Bookingtime();
        bookingtime2.setId(bookingtime1.getId());
        assertThat(bookingtime1).isEqualTo(bookingtime2);
        bookingtime2.setId(2L);
        assertThat(bookingtime1).isNotEqualTo(bookingtime2);
        bookingtime1.setId(null);
        assertThat(bookingtime1).isNotEqualTo(bookingtime2);
    }
}
