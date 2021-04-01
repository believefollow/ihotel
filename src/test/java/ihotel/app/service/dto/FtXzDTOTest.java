package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FtXzDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FtXzDTO.class);
        FtXzDTO ftXzDTO1 = new FtXzDTO();
        ftXzDTO1.setId(1L);
        FtXzDTO ftXzDTO2 = new FtXzDTO();
        assertThat(ftXzDTO1).isNotEqualTo(ftXzDTO2);
        ftXzDTO2.setId(ftXzDTO1.getId());
        assertThat(ftXzDTO1).isEqualTo(ftXzDTO2);
        ftXzDTO2.setId(2L);
        assertThat(ftXzDTO1).isNotEqualTo(ftXzDTO2);
        ftXzDTO1.setId(null);
        assertThat(ftXzDTO1).isNotEqualTo(ftXzDTO2);
    }
}
