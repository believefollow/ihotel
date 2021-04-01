package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckinBakDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckinBakDTO.class);
        CheckinBakDTO checkinBakDTO1 = new CheckinBakDTO();
        checkinBakDTO1.setId(1L);
        CheckinBakDTO checkinBakDTO2 = new CheckinBakDTO();
        assertThat(checkinBakDTO1).isNotEqualTo(checkinBakDTO2);
        checkinBakDTO2.setId(checkinBakDTO1.getId());
        assertThat(checkinBakDTO1).isEqualTo(checkinBakDTO2);
        checkinBakDTO2.setId(2L);
        assertThat(checkinBakDTO1).isNotEqualTo(checkinBakDTO2);
        checkinBakDTO1.setId(null);
        assertThat(checkinBakDTO1).isNotEqualTo(checkinBakDTO2);
    }
}
