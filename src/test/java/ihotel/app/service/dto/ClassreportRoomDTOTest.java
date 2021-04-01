package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassreportRoomDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassreportRoomDTO.class);
        ClassreportRoomDTO classreportRoomDTO1 = new ClassreportRoomDTO();
        classreportRoomDTO1.setId(1L);
        ClassreportRoomDTO classreportRoomDTO2 = new ClassreportRoomDTO();
        assertThat(classreportRoomDTO1).isNotEqualTo(classreportRoomDTO2);
        classreportRoomDTO2.setId(classreportRoomDTO1.getId());
        assertThat(classreportRoomDTO1).isEqualTo(classreportRoomDTO2);
        classreportRoomDTO2.setId(2L);
        assertThat(classreportRoomDTO1).isNotEqualTo(classreportRoomDTO2);
        classreportRoomDTO1.setId(null);
        assertThat(classreportRoomDTO1).isNotEqualTo(classreportRoomDTO2);
    }
}
