package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassreportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classreport.class);
        Classreport classreport1 = new Classreport();
        classreport1.setId(1L);
        Classreport classreport2 = new Classreport();
        classreport2.setId(classreport1.getId());
        assertThat(classreport1).isEqualTo(classreport2);
        classreport2.setId(2L);
        assertThat(classreport1).isNotEqualTo(classreport2);
        classreport1.setId(null);
        assertThat(classreport1).isNotEqualTo(classreport2);
    }
}
