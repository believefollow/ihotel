package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookingtimeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookingtimeDTO.class);
        BookingtimeDTO bookingtimeDTO1 = new BookingtimeDTO();
        bookingtimeDTO1.setId(1L);
        BookingtimeDTO bookingtimeDTO2 = new BookingtimeDTO();
        assertThat(bookingtimeDTO1).isNotEqualTo(bookingtimeDTO2);
        bookingtimeDTO2.setId(bookingtimeDTO1.getId());
        assertThat(bookingtimeDTO1).isEqualTo(bookingtimeDTO2);
        bookingtimeDTO2.setId(2L);
        assertThat(bookingtimeDTO1).isNotEqualTo(bookingtimeDTO2);
        bookingtimeDTO1.setId(null);
        assertThat(bookingtimeDTO1).isNotEqualTo(bookingtimeDTO2);
    }
}
