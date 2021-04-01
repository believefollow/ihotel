package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FtXzsMapperTest {

    private FtXzsMapper ftXzsMapper;

    @BeforeEach
    public void setUp() {
        ftXzsMapper = new FtXzsMapperImpl();
    }
}
