package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassBakDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassBakDTO.class);
        ClassBakDTO classBakDTO1 = new ClassBakDTO();
        classBakDTO1.setId(1L);
        ClassBakDTO classBakDTO2 = new ClassBakDTO();
        assertThat(classBakDTO1).isNotEqualTo(classBakDTO2);
        classBakDTO2.setId(classBakDTO1.getId());
        assertThat(classBakDTO1).isEqualTo(classBakDTO2);
        classBakDTO2.setId(2L);
        assertThat(classBakDTO1).isNotEqualTo(classBakDTO2);
        classBakDTO1.setId(null);
        assertThat(classBakDTO1).isNotEqualTo(classBakDTO2);
    }
}
