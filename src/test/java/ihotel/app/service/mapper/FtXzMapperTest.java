package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FtXzMapperTest {

    private FtXzMapper ftXzMapper;

    @BeforeEach
    public void setUp() {
        ftXzMapper = new FtXzMapperImpl();
    }
}
