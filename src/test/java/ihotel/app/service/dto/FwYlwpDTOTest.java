package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FwYlwpDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FwYlwpDTO.class);
        FwYlwpDTO fwYlwpDTO1 = new FwYlwpDTO();
        fwYlwpDTO1.setId(1L);
        FwYlwpDTO fwYlwpDTO2 = new FwYlwpDTO();
        assertThat(fwYlwpDTO1).isNotEqualTo(fwYlwpDTO2);
        fwYlwpDTO2.setId(fwYlwpDTO1.getId());
        assertThat(fwYlwpDTO1).isEqualTo(fwYlwpDTO2);
        fwYlwpDTO2.setId(2L);
        assertThat(fwYlwpDTO1).isNotEqualTo(fwYlwpDTO2);
        fwYlwpDTO1.setId(null);
        assertThat(fwYlwpDTO1).isNotEqualTo(fwYlwpDTO2);
    }
}
