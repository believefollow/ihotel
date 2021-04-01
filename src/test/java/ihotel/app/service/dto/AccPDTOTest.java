package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccPDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccPDTO.class);
        AccPDTO accPDTO1 = new AccPDTO();
        accPDTO1.setId(1L);
        AccPDTO accPDTO2 = new AccPDTO();
        assertThat(accPDTO1).isNotEqualTo(accPDTO2);
        accPDTO2.setId(accPDTO1.getId());
        assertThat(accPDTO1).isEqualTo(accPDTO2);
        accPDTO2.setId(2L);
        assertThat(accPDTO1).isNotEqualTo(accPDTO2);
        accPDTO1.setId(null);
        assertThat(accPDTO1).isNotEqualTo(accPDTO2);
    }
}
