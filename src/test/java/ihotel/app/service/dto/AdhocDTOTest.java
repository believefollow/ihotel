package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdhocDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdhocDTO.class);
        AdhocDTO adhocDTO1 = new AdhocDTO();
        adhocDTO1.setId("id1");
        AdhocDTO adhocDTO2 = new AdhocDTO();
        assertThat(adhocDTO1).isNotEqualTo(adhocDTO2);
        adhocDTO2.setId(adhocDTO1.getId());
        assertThat(adhocDTO1).isEqualTo(adhocDTO2);
        adhocDTO2.setId("id2");
        assertThat(adhocDTO1).isNotEqualTo(adhocDTO2);
        adhocDTO1.setId(null);
        assertThat(adhocDTO1).isNotEqualTo(adhocDTO2);
    }
}
