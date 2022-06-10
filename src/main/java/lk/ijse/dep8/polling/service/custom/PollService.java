package lk.ijse.dep8.polling.service.custom;

import lk.ijse.dep8.polling.dto.PollDTO;
import lk.ijse.dep8.polling.service.SuperService;
import lk.ijse.dep8.polling.service.exception.NotFoundException;

import java.util.List;

public interface PollService extends SuperService {
    List<PollDTO> listAllPoll();
    PollDTO getPoll(int id) throws NotFoundException;
    PollDTO savePoll(PollDTO dto);
    void updatePoll(PollDTO dto) throws NotFoundException;
    void deletePoll(int id) throws NotFoundException;

}
