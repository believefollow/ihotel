package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FtXzsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FtXzsDTO.class);
        FtXzsDTO ftXzsDTO1 = new FtXzsDTO();
        ftXzsDTO1.setId(1L);
        FtXzsDTO ftXzsDTO2 = new FtXzsDTO();
        assertThat(ftXzsDTO1).isNotEqualTo(ftXzsDTO2);
        ftXzsDTO2.setId(ftXzsDTO1.getId());
        assertThat(ftXzsDTO1).isEqualTo(ftXzsDTO2);
        ftXzsDTO2.setId(2L);
        assertThat(ftXzsDTO1).isNotEqualTo(ftXzsDTO2);
        ftXzsDTO1.setId(null);
        assertThat(ftXzsDTO1).isNotEqualTo(ftXzsDTO2);
    }
}
