package lk.ijse.dep8.polling.listener;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.setProperty("app.profiles.active","dev");
    }
}
