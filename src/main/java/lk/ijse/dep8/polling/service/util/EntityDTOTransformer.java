package lk.ijse.dep8.polling.service.util;

import lk.ijse.dep8.polling.dto.PollDTO;
import lk.ijse.dep8.polling.entity.Poll;
import org.modelmapper.ModelMapper;

public class EntityDTOTransformer {
    public static PollDTO getPolDTO(Poll pollEntity){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(pollEntity,PollDTO.class);

    }
    public static Poll getPollEntity(PollDTO pollDTO){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(pollDTO,Poll.class);
    }
}
