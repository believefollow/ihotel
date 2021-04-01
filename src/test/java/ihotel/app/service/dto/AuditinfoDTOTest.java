package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditinfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditinfoDTO.class);
        AuditinfoDTO auditinfoDTO1 = new AuditinfoDTO();
        auditinfoDTO1.setId(1L);
        AuditinfoDTO auditinfoDTO2 = new AuditinfoDTO();
        assertThat(auditinfoDTO1).isNotEqualTo(auditinfoDTO2);
        auditinfoDTO2.setId(auditinfoDTO1.getId());
        assertThat(auditinfoDTO1).isEqualTo(auditinfoDTO2);
        auditinfoDTO2.setId(2L);
        assertThat(auditinfoDTO1).isNotEqualTo(auditinfoDTO2);
        auditinfoDTO1.setId(null);
        assertThat(auditinfoDTO1).isNotEqualTo(auditinfoDTO2);
    }
}
