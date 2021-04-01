package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FtXzTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FtXz.class);
        FtXz ftXz1 = new FtXz();
        ftXz1.setId(1L);
        FtXz ftXz2 = new FtXz();
        ftXz2.setId(ftXz1.getId());
        assertThat(ftXz1).isEqualTo(ftXz2);
        ftXz2.setId(2L);
        assertThat(ftXz1).isNotEqualTo(ftXz2);
        ftXz1.setId(null);
        assertThat(ftXz1).isNotEqualTo(ftXz2);
    }
}
