package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CyRoomtypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CyRoomtype.class);
        CyRoomtype cyRoomtype1 = new CyRoomtype();
        cyRoomtype1.setId(1L);
        CyRoomtype cyRoomtype2 = new CyRoomtype();
        cyRoomtype2.setId(cyRoomtype1.getId());
        assertThat(cyRoomtype1).isEqualTo(cyRoomtype2);
        cyRoomtype2.setId(2L);
        assertThat(cyRoomtype1).isNotEqualTo(cyRoomtype2);
        cyRoomtype1.setId(null);
        assertThat(cyRoomtype1).isNotEqualTo(cyRoomtype2);
    }
}
