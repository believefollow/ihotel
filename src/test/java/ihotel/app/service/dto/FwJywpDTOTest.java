package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FwJywpDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FwJywpDTO.class);
        FwJywpDTO fwJywpDTO1 = new FwJywpDTO();
        fwJywpDTO1.setId(1L);
        FwJywpDTO fwJywpDTO2 = new FwJywpDTO();
        assertThat(fwJywpDTO1).isNotEqualTo(fwJywpDTO2);
        fwJywpDTO2.setId(fwJywpDTO1.getId());
        assertThat(fwJywpDTO1).isEqualTo(fwJywpDTO2);
        fwJywpDTO2.setId(2L);
        assertThat(fwJywpDTO1).isNotEqualTo(fwJywpDTO2);
        fwJywpDTO1.setId(null);
        assertThat(fwJywpDTO1).isNotEqualTo(fwJywpDTO2);
    }
}
