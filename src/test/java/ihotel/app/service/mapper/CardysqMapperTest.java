package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardysqMapperTest {

    private CardysqMapper cardysqMapper;

    @BeforeEach
    public void setUp() {
        cardysqMapper = new CardysqMapperImpl();
    }
}
