package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdhocMapperTest {

    private AdhocMapper adhocMapper;

    @BeforeEach
    public void setUp() {
        adhocMapper = new AdhocMapperImpl();
    }
}
