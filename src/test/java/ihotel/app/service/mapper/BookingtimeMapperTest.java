package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookingtimeMapperTest {

    private BookingtimeMapper bookingtimeMapper;

    @BeforeEach
    public void setUp() {
        bookingtimeMapper = new BookingtimeMapperImpl();
    }
}
