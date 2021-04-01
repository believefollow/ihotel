package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditinfoMapperTest {

    private AuditinfoMapper auditinfoMapper;

    @BeforeEach
    public void setUp() {
        auditinfoMapper = new AuditinfoMapperImpl();
    }
}
