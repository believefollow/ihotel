package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccPpDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccPpDTO.class);
        AccPpDTO accPpDTO1 = new AccPpDTO();
        accPpDTO1.setId(1L);
        AccPpDTO accPpDTO2 = new AccPpDTO();
        assertThat(accPpDTO1).isNotEqualTo(accPpDTO2);
        accPpDTO2.setId(accPpDTO1.getId());
        assertThat(accPpDTO1).isEqualTo(accPpDTO2);
        accPpDTO2.setId(2L);
        assertThat(accPpDTO1).isNotEqualTo(accPpDTO2);
        accPpDTO1.setId(null);
        assertThat(accPpDTO1).isNotEqualTo(accPpDTO2);
    }
}
