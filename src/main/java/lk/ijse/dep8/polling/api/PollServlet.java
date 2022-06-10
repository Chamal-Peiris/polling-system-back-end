package lk.ijse.dep8.polling.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import lk.ijse.dep8.polling.dto.PollDTO;
import lk.ijse.dep8.polling.util.HttpServlet2;
import lk.ijse.dep8.polling.util.ResponseStatusException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "polServlet",urlPatterns = "/api/v1/polls/*")
public class PollServlet extends HttpServlet2 {
    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("patch");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       if(req.getPathInfo()==null||req.getPathInfo().equals("/")){
           System.out.println("get all polls");
       }else{

           Matcher matcher = Pattern.compile("^/(\\d+)/?$").matcher(req.getPathInfo());
           if(!req.getPathInfo().matches("/(\\d+)/?")){
               System.out.println("Invalid");
           }
           int polId=Integer.parseInt(matcher.group(1));
           //System.out.println("Get a Poll");
           /*Todo:Get a poll from the service layer*/

       }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("delete");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*validate the url*/
        if(req.getPathInfo()!=null&&!req.getPathInfo().equals("/")){
            System.out.println("Invalid End Point");
        }
        /*validate the content type*/
        if(req.getContentType()==null||!req.getContentType().toLowerCase().startsWith("application/json")){
            System.out.println("Invalid content Type");
        }

        try{
            Jsonb jsonb = JsonbBuilder.create();
            PollDTO pollDTO = jsonb.fromJson(req.getReader(), PollDTO.class);
            if(pollDTO.getId()!=null){
                throw new ResponseStatusException(400,"Id should be empty");
            } else if (pollDTO.getCreatedBy()==null||pollDTO.getCreatedBy().trim().isEmpty()) {
                throw new ResponseStatusException(400,"Invalid user");
            }else if(pollDTO.getUpVotes() != 0||pollDTO.getDownVotes()!=0){
                throw new ResponseStatusException(400,"Invalid votes");
            } else if (pollDTO.getTitle()==null||pollDTO.getTitle().trim().isEmpty()) {
                throw new ResponseStatusException(400,"Invalid title");
            }

            /*Todo:Request to save this pol from service*/
        }catch (JsonbException e){
            throw new ResponseStatusException(400,"Invalid JSON");
        }
    }
}
