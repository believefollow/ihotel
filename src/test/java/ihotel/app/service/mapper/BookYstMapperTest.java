package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookYstMapperTest {

    private BookYstMapper bookYstMapper;

    @BeforeEach
    public void setUp() {
        bookYstMapper = new BookYstMapperImpl();
    }
}
