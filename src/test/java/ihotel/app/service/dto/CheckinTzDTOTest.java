package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckinTzDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckinTzDTO.class);
        CheckinTzDTO checkinTzDTO1 = new CheckinTzDTO();
        checkinTzDTO1.setId(1L);
        CheckinTzDTO checkinTzDTO2 = new CheckinTzDTO();
        assertThat(checkinTzDTO1).isNotEqualTo(checkinTzDTO2);
        checkinTzDTO2.setId(checkinTzDTO1.getId());
        assertThat(checkinTzDTO1).isEqualTo(checkinTzDTO2);
        checkinTzDTO2.setId(2L);
        assertThat(checkinTzDTO1).isNotEqualTo(checkinTzDTO2);
        checkinTzDTO1.setId(null);
        assertThat(checkinTzDTO1).isNotEqualTo(checkinTzDTO2);
    }
}
