package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassRenameTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassRename.class);
        ClassRename classRename1 = new ClassRename();
        classRename1.setId(1L);
        ClassRename classRename2 = new ClassRename();
        classRename2.setId(classRename1.getId());
        assertThat(classRename1).isEqualTo(classRename2);
        classRename2.setId(2L);
        assertThat(classRename1).isNotEqualTo(classRename2);
        classRename1.setId(null);
        assertThat(classRename1).isNotEqualTo(classRename2);
    }
}
