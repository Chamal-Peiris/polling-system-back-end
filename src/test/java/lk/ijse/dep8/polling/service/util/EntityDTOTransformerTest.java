package lk.ijse.dep8.polling.service.util;

import lk.ijse.dep8.polling.dto.PollDTO;
import lk.ijse.dep8.polling.entity.Poll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityDTOTransformerTest {


    @Test
    void getPolDTO() {
        Poll poll = new Poll("Home", 10, 20, "chamal");
        PollDTO polDTO = EntityDTOTransformer.getPolDTO(poll);
        assertEquals(polDTO.getDownVotes(),poll.getDownVotes());


    }

    @Test
    void getPollEntity() {
        PollDTO pollDTO = new PollDTO("Home", 10, 20, "chamal");
        Poll pollEntity = EntityDTOTransformer.getPollEntity(pollDTO);
        assertEquals(pollEntity.getTitle(),pollDTO.getTitle());


    }
}