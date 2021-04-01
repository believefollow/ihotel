package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChoiceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChoiceDTO.class);
        ChoiceDTO choiceDTO1 = new ChoiceDTO();
        choiceDTO1.setId(1L);
        ChoiceDTO choiceDTO2 = new ChoiceDTO();
        assertThat(choiceDTO1).isNotEqualTo(choiceDTO2);
        choiceDTO2.setId(choiceDTO1.getId());
        assertThat(choiceDTO1).isEqualTo(choiceDTO2);
        choiceDTO2.setId(2L);
        assertThat(choiceDTO1).isNotEqualTo(choiceDTO2);
        choiceDTO1.setId(null);
        assertThat(choiceDTO1).isNotEqualTo(choiceDTO2);
    }
}
