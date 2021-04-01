package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FwWxfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FwWxfDTO.class);
        FwWxfDTO fwWxfDTO1 = new FwWxfDTO();
        fwWxfDTO1.setId(1L);
        FwWxfDTO fwWxfDTO2 = new FwWxfDTO();
        assertThat(fwWxfDTO1).isNotEqualTo(fwWxfDTO2);
        fwWxfDTO2.setId(fwWxfDTO1.getId());
        assertThat(fwWxfDTO1).isEqualTo(fwWxfDTO2);
        fwWxfDTO2.setId(2L);
        assertThat(fwWxfDTO1).isNotEqualTo(fwWxfDTO2);
        fwWxfDTO1.setId(null);
        assertThat(fwWxfDTO1).isNotEqualTo(fwWxfDTO2);
    }
}
