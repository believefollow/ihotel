package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DUnitDTO.class);
        DUnitDTO dUnitDTO1 = new DUnitDTO();
        dUnitDTO1.setId(1L);
        DUnitDTO dUnitDTO2 = new DUnitDTO();
        assertThat(dUnitDTO1).isNotEqualTo(dUnitDTO2);
        dUnitDTO2.setId(dUnitDTO1.getId());
        assertThat(dUnitDTO1).isEqualTo(dUnitDTO2);
        dUnitDTO2.setId(2L);
        assertThat(dUnitDTO1).isNotEqualTo(dUnitDTO2);
        dUnitDTO1.setId(null);
        assertThat(dUnitDTO1).isNotEqualTo(dUnitDTO2);
    }
}
