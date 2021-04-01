package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassRenameMapperTest {

    private ClassRenameMapper classRenameMapper;

    @BeforeEach
    public void setUp() {
        classRenameMapper = new ClassRenameMapperImpl();
    }
}
