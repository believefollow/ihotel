package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckinAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckinAccountDTO.class);
        CheckinAccountDTO checkinAccountDTO1 = new CheckinAccountDTO();
        checkinAccountDTO1.setId(1L);
        CheckinAccountDTO checkinAccountDTO2 = new CheckinAccountDTO();
        assertThat(checkinAccountDTO1).isNotEqualTo(checkinAccountDTO2);
        checkinAccountDTO2.setId(checkinAccountDTO1.getId());
        assertThat(checkinAccountDTO1).isEqualTo(checkinAccountDTO2);
        checkinAccountDTO2.setId(2L);
        assertThat(checkinAccountDTO1).isNotEqualTo(checkinAccountDTO2);
        checkinAccountDTO1.setId(null);
        assertThat(checkinAccountDTO1).isNotEqualTo(checkinAccountDTO2);
    }
}
