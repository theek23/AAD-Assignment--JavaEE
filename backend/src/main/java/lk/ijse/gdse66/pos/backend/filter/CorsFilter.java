package lk.ijse.gdse66.pos.backend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CorsFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("Core filter req Received");
        res.addHeader("Access-Control-Allow-Origin","*");
        res.addHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,OPTION");
        res.addHeader("Access-Control-Allow-Headers","Content-Type");
        chain.doFilter(req, res);
        System.out.println("Core filter Response out");
    }
}
