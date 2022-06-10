package lk.ijse.dep8.polling.api;

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
       }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("delete");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post");
    }
}
