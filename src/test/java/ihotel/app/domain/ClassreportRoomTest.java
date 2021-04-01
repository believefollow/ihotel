package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassreportRoomTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassreportRoom.class);
        ClassreportRoom classreportRoom1 = new ClassreportRoom();
        classreportRoom1.setId(1L);
        ClassreportRoom classreportRoom2 = new ClassreportRoom();
        classreportRoom2.setId(classreportRoom1.getId());
        assertThat(classreportRoom1).isEqualTo(classreportRoom2);
        classreportRoom2.setId(2L);
        assertThat(classreportRoom1).isNotEqualTo(classreportRoom2);
        classreportRoom1.setId(null);
        assertThat(classreportRoom1).isNotEqualTo(classreportRoom2);
    }
}
