package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassBakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassBak.class);
        ClassBak classBak1 = new ClassBak();
        classBak1.setId(1L);
        ClassBak classBak2 = new ClassBak();
        classBak2.setId(classBak1.getId());
        assertThat(classBak1).isEqualTo(classBak2);
        classBak2.setId(2L);
        assertThat(classBak1).isNotEqualTo(classBak2);
        classBak1.setId(null);
        assertThat(classBak1).isNotEqualTo(classBak2);
    }
}
