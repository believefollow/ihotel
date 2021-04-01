package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassRenameDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassRenameDTO.class);
        ClassRenameDTO classRenameDTO1 = new ClassRenameDTO();
        classRenameDTO1.setId(1L);
        ClassRenameDTO classRenameDTO2 = new ClassRenameDTO();
        assertThat(classRenameDTO1).isNotEqualTo(classRenameDTO2);
        classRenameDTO2.setId(classRenameDTO1.getId());
        assertThat(classRenameDTO1).isEqualTo(classRenameDTO2);
        classRenameDTO2.setId(2L);
        assertThat(classRenameDTO1).isNotEqualTo(classRenameDTO2);
        classRenameDTO1.setId(null);
        assertThat(classRenameDTO1).isNotEqualTo(classRenameDTO2);
    }
}
