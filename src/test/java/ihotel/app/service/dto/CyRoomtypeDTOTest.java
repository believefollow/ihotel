package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CyRoomtypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CyRoomtypeDTO.class);
        CyRoomtypeDTO cyRoomtypeDTO1 = new CyRoomtypeDTO();
        cyRoomtypeDTO1.setId(1L);
        CyRoomtypeDTO cyRoomtypeDTO2 = new CyRoomtypeDTO();
        assertThat(cyRoomtypeDTO1).isNotEqualTo(cyRoomtypeDTO2);
        cyRoomtypeDTO2.setId(cyRoomtypeDTO1.getId());
        assertThat(cyRoomtypeDTO1).isEqualTo(cyRoomtypeDTO2);
        cyRoomtypeDTO2.setId(2L);
        assertThat(cyRoomtypeDTO1).isNotEqualTo(cyRoomtypeDTO2);
        cyRoomtypeDTO1.setId(null);
        assertThat(cyRoomtypeDTO1).isNotEqualTo(cyRoomtypeDTO2);
    }
}
