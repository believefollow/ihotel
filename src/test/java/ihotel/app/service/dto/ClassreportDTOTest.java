package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassreportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassreportDTO.class);
        ClassreportDTO classreportDTO1 = new ClassreportDTO();
        classreportDTO1.setId(1L);
        ClassreportDTO classreportDTO2 = new ClassreportDTO();
        assertThat(classreportDTO1).isNotEqualTo(classreportDTO2);
        classreportDTO2.setId(classreportDTO1.getId());
        assertThat(classreportDTO1).isEqualTo(classreportDTO2);
        classreportDTO2.setId(2L);
        assertThat(classreportDTO1).isNotEqualTo(classreportDTO2);
        classreportDTO1.setId(null);
        assertThat(classreportDTO1).isNotEqualTo(classreportDTO2);
    }
}
