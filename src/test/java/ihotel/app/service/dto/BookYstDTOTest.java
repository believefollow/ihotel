package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookYstDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookYstDTO.class);
        BookYstDTO bookYstDTO1 = new BookYstDTO();
        bookYstDTO1.setId(1L);
        BookYstDTO bookYstDTO2 = new BookYstDTO();
        assertThat(bookYstDTO1).isNotEqualTo(bookYstDTO2);
        bookYstDTO2.setId(bookYstDTO1.getId());
        assertThat(bookYstDTO1).isEqualTo(bookYstDTO2);
        bookYstDTO2.setId(2L);
        assertThat(bookYstDTO1).isNotEqualTo(bookYstDTO2);
        bookYstDTO1.setId(null);
        assertThat(bookYstDTO1).isNotEqualTo(bookYstDTO2);
    }
}
