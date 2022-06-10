package lk.ijse.dep8.polling.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import lk.ijse.dep8.polling.dto.PollDTO;
import lk.ijse.dep8.polling.service.ServiceFactory;
import lk.ijse.dep8.polling.service.SuperService;
import lk.ijse.dep8.polling.service.custom.PollService;
import lk.ijse.dep8.polling.service.exception.NotFoundException;
import lk.ijse.dep8.polling.util.HttpServlet2;
import lk.ijse.dep8.polling.util.ResponseStatusException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "polServlet", urlPatterns = "/api/v1/polls/*")
public class PollServlet extends HttpServlet2 {

    private int getPollId(HttpServletRequest req) {
        if (req.getPathInfo() == null) throw new ResponseStatusException(404, "Invalid end point");
        Matcher matcher = Pattern.compile("^/(\\d+)/?$").matcher(req.getPathInfo());
        if (!matcher.find()) throw new ResponseStatusException(404, "Invalid poll id");
        return Integer.parseInt(matcher.group(1));
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*validate the url*/
        int pollId = getPollId(req);

        /*validate the content type*/
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            System.out.println("Invalid content Type");
        }

        try {
            Jsonb jsonb = JsonbBuilder.create();
            PollDTO pollDTO = jsonb.fromJson(req.getReader(), PollDTO.class);
            if (pollDTO.getId() != null && pollDTO.getId() != pollId) {
                throw new ResponseStatusException(400, "Id Mismatch error");
            } else if (pollDTO.getCreatedBy() == null || pollDTO.getCreatedBy().trim().isEmpty()) {
                throw new ResponseStatusException(400, "Invalid user");
            } else if (pollDTO.getUpVotes() == null || pollDTO.getDownVotes() == null) {
                throw new ResponseStatusException(400, "Insert the up and down votes");
            } else if (pollDTO.getUpVotes() < 0 || pollDTO.getDownVotes() < 0) {
                throw new ResponseStatusException(400, "vote count shouldn't be negative");
            } else if (pollDTO.getTitle() == null || pollDTO.getTitle().trim().isEmpty()) {
                throw new ResponseStatusException(400, "Invalid title");
            }
            PollService pollService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.POLL);
            pollDTO.setId(pollId);
            pollService.updatePoll(pollDTO);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

            /*Todo:Request to update this pol from service layer*/
        } catch (JsonbException e) {
            throw new ResponseStatusException(400, "Invalid JSON");
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PollService pollService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.POLL);
        Jsonb jsonb = JsonbBuilder.create();
        if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            System.out.println("get all polls");
            List<PollDTO> pollDTOS = pollService.listAllPoll();
            resp.setContentType("application/json");
            jsonb.toJson(pollDTOS, resp.getWriter());

        } else {

            /*Todo:Get a poll from the service layer*/
            int polId = getPollId(req);
            PollDTO poll = null;
            try {
                poll = pollService.getPoll(polId);
                resp.setContentType("application/json");
                jsonb.toJson(poll, resp.getWriter());
            } catch (NotFoundException e) {
                throw new ResponseStatusException(404,"Invalid ID");
            }

            //System.out.println("Get a Poll");


        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*validate the URL*/
        int polId = getPollId(req);
        PollService pollService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.POLL);
        try {
            pollService.deletePoll(polId);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        /*todo: Send a request to delete this poll to the service layer*/

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*validate the url*/
        if (req.getPathInfo() != null && !req.getPathInfo().equals("/")) {
            System.out.println("Invalid End Point");
        }
        /*validate the content type*/
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            System.out.println("Invalid content Type");
        }

        try {
            Jsonb jsonb = JsonbBuilder.create();
            PollDTO pollDTO = jsonb.fromJson(req.getReader(), PollDTO.class);
            if (pollDTO.getId() != null) {
                throw new ResponseStatusException(400, "Id should be empty");
            } else if (pollDTO.getCreatedBy() == null || pollDTO.getCreatedBy().trim().isEmpty()) {
                throw new ResponseStatusException(400, "Invalid user");
            } else if ((pollDTO.getUpVotes() != null && pollDTO.getUpVotes() != 0) || (pollDTO.getUpVotes() != null && pollDTO.getDownVotes() != 0)) {
                throw new ResponseStatusException(400, "Invalid votes");
            } else if (pollDTO.getTitle() == null || pollDTO.getTitle().trim().isEmpty()) {
                throw new ResponseStatusException(400, "Invalid title");
            }
            PollService pollService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.POLL);
            PollDTO pollDTO1 = pollService.savePoll(pollDTO);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_CREATED);
            jsonb.toJson(pollDTO1, resp.getWriter());

            /*Todo:Request to save this pol from service*/
        } catch (JsonbException e) {
            throw new ResponseStatusException(400, "Invalid JSON");
        }
    }
}
