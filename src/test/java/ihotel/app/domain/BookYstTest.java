package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookYstTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookYst.class);
        BookYst bookYst1 = new BookYst();
        bookYst1.setId(1L);
        BookYst bookYst2 = new BookYst();
        bookYst2.setId(bookYst1.getId());
        assertThat(bookYst1).isEqualTo(bookYst2);
        bookYst2.setId(2L);
        assertThat(bookYst1).isNotEqualTo(bookYst2);
        bookYst1.setId(null);
        assertThat(bookYst1).isNotEqualTo(bookYst2);
    }
}
