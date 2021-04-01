package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassBakMapperTest {

    private ClassBakMapper classBakMapper;

    @BeforeEach
    public void setUp() {
        classBakMapper = new ClassBakMapperImpl();
    }
}
