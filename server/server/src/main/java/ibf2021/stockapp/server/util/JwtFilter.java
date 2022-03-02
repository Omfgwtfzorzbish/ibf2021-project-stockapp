package ibf2021.stockapp.server.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import ibf2021.stockapp.server.services.LoginServ;

@Service
public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoginServ loginSvc;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterchain)throws ServletException,IOException{
        String token = null; String username = null; 
        String authorizationHeader = request.getHeader("Authorization"); //"Authorization":"Bearer ljlasdjg.asdljjs<jsonkey>"
            if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer")){
                token=authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(token);
            }
            if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
                 loginSvc.loadUserByUsername(username);
                 UserDetails userDetails = loginSvc.loadUserByUsername(username);
                    if(jwtUtil.validateToken(token, userDetails)){
                        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(upat);
                    }
            } 
            filterchain.doFilter(request, response);
    }
}
