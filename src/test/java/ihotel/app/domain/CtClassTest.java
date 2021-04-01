package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CtClassTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CtClass.class);
        CtClass ctClass1 = new CtClass();
        ctClass1.setId(1L);
        CtClass ctClass2 = new CtClass();
        ctClass2.setId(ctClass1.getId());
        assertThat(ctClass1).isEqualTo(ctClass2);
        ctClass2.setId(2L);
        assertThat(ctClass1).isNotEqualTo(ctClass2);
        ctClass1.setId(null);
        assertThat(ctClass1).isNotEqualTo(ctClass2);
    }
}
