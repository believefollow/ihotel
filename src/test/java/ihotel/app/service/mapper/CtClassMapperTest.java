package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CtClassMapperTest {

    private CtClassMapper ctClassMapper;

    @BeforeEach
    public void setUp() {
        ctClassMapper = new CtClassMapperImpl();
    }
}
