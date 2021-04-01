package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditinfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auditinfo.class);
        Auditinfo auditinfo1 = new Auditinfo();
        auditinfo1.setId(1L);
        Auditinfo auditinfo2 = new Auditinfo();
        auditinfo2.setId(auditinfo1.getId());
        assertThat(auditinfo1).isEqualTo(auditinfo2);
        auditinfo2.setId(2L);
        assertThat(auditinfo1).isNotEqualTo(auditinfo2);
        auditinfo1.setId(null);
        assertThat(auditinfo1).isNotEqualTo(auditinfo2);
    }
}
