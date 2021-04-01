package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DCompanyMapperTest {

    private DCompanyMapper dCompanyMapper;

    @BeforeEach
    public void setUp() {
        dCompanyMapper = new DCompanyMapperImpl();
    }
}
