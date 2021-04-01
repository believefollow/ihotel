package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CardysqDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardysqDTO.class);
        CardysqDTO cardysqDTO1 = new CardysqDTO();
        cardysqDTO1.setId(1L);
        CardysqDTO cardysqDTO2 = new CardysqDTO();
        assertThat(cardysqDTO1).isNotEqualTo(cardysqDTO2);
        cardysqDTO2.setId(cardysqDTO1.getId());
        assertThat(cardysqDTO1).isEqualTo(cardysqDTO2);
        cardysqDTO2.setId(2L);
        assertThat(cardysqDTO1).isNotEqualTo(cardysqDTO2);
        cardysqDTO1.setId(null);
        assertThat(cardysqDTO1).isNotEqualTo(cardysqDTO2);
    }
}
