package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FtXzsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FtXzs.class);
        FtXzs ftXzs1 = new FtXzs();
        ftXzs1.setId(1L);
        FtXzs ftXzs2 = new FtXzs();
        ftXzs2.setId(ftXzs1.getId());
        assertThat(ftXzs1).isEqualTo(ftXzs2);
        ftXzs2.setId(2L);
        assertThat(ftXzs1).isNotEqualTo(ftXzs2);
        ftXzs1.setId(null);
        assertThat(ftXzs1).isNotEqualTo(ftXzs2);
    }
}
