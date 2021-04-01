package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DayearndetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayearndetailDTO.class);
        DayearndetailDTO dayearndetailDTO1 = new DayearndetailDTO();
        dayearndetailDTO1.setId(1L);
        DayearndetailDTO dayearndetailDTO2 = new DayearndetailDTO();
        assertThat(dayearndetailDTO1).isNotEqualTo(dayearndetailDTO2);
        dayearndetailDTO2.setId(dayearndetailDTO1.getId());
        assertThat(dayearndetailDTO1).isEqualTo(dayearndetailDTO2);
        dayearndetailDTO2.setId(2L);
        assertThat(dayearndetailDTO1).isNotEqualTo(dayearndetailDTO2);
        dayearndetailDTO1.setId(null);
        assertThat(dayearndetailDTO1).isNotEqualTo(dayearndetailDTO2);
    }
}
