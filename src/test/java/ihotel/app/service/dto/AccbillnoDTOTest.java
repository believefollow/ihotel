package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccbillnoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccbillnoDTO.class);
        AccbillnoDTO accbillnoDTO1 = new AccbillnoDTO();
        accbillnoDTO1.setId(1L);
        AccbillnoDTO accbillnoDTO2 = new AccbillnoDTO();
        assertThat(accbillnoDTO1).isNotEqualTo(accbillnoDTO2);
        accbillnoDTO2.setId(accbillnoDTO1.getId());
        assertThat(accbillnoDTO1).isEqualTo(accbillnoDTO2);
        accbillnoDTO2.setId(2L);
        assertThat(accbillnoDTO1).isNotEqualTo(accbillnoDTO2);
        accbillnoDTO1.setId(null);
        assertThat(accbillnoDTO1).isNotEqualTo(accbillnoDTO2);
    }
}
