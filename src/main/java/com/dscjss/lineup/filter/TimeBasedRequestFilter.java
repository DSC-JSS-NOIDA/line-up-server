package com.dscjss.lineup.filter;

import com.dscjss.lineup.game.GameDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TimeBasedRequestFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(TimeBasedRequestFilter.class);

    private final GameDetails gameDetails;


    @Autowired
    public TimeBasedRequestFilter(GameDetails gameDetails) {
        this.gameDetails = gameDetails;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;


        Instant now = Instant.now();
        boolean filter = true;
        if(httpServletRequest.getRequestURI().startsWith("/api/admin")){
            logger.info("Filtered Request {}.", httpServletRequest.getRequestURI());
            filter = true;
            logger.info("Filtered Request Admin request.");
        }else if(httpServletRequest.getRequestURI().equals("/oauth/token")){
            logger.info("Filtered Request {}.", httpServletRequest.getRequestURI());
            filter = true;
            logger.info("Filtered Request Login request.");
        }else if(httpServletRequest.getRequestURI().equals("/api/event_details")){
            logger.info("Filtered Request {}.", httpServletRequest.getRequestURI());
            filter = true;
            logger.info("Filtered Request Even details request.");
        }else if(gameDetails.getSettings().getSignUpStartTime().isAfter(now)){
            logger.info("Filtered Request {}.", httpServletRequest.getRequestURI());
            httpServletResponse.setStatus(HttpStatus.PRECONDITION_REQUIRED.value());
            filter = false;
            logger.info("Filtered Request SignUp not started.");
        }else if(httpServletRequest.getRequestURI().equals("/api/signup")){
            logger.info("Filtered Request {}.", httpServletRequest.getRequestURI());
            filter = true;
            logger.info("Filtered Request SignUp started.");
        } else if(gameDetails.getSettings().getStartTime().isAfter(now)){
            logger.info("Filtered Request {}.", httpServletRequest.getRequestURI());
            httpServletResponse.setStatus(HttpStatus.PRECONDITION_REQUIRED.value());
            filter = false;
            logger.info("Filtered Request Contest not started.");
        }

        if(filter){
            filterChain.doFilter(servletRequest, servletResponse);
        }



    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
