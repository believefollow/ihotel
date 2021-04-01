package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DPdbDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DPdbDTO.class);
        DPdbDTO dPdbDTO1 = new DPdbDTO();
        dPdbDTO1.setId(1L);
        DPdbDTO dPdbDTO2 = new DPdbDTO();
        assertThat(dPdbDTO1).isNotEqualTo(dPdbDTO2);
        dPdbDTO2.setId(dPdbDTO1.getId());
        assertThat(dPdbDTO1).isEqualTo(dPdbDTO2);
        dPdbDTO2.setId(2L);
        assertThat(dPdbDTO1).isNotEqualTo(dPdbDTO2);
        dPdbDTO1.setId(null);
        assertThat(dPdbDTO1).isNotEqualTo(dPdbDTO2);
    }
}
