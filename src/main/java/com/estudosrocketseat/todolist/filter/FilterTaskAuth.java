package com.estudosrocketseat.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.estudosrocketseat.todolist.user.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    private UserRepository userRepository;

    @Autowired
    public FilterTaskAuth(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if(servletPath.startsWith("/tasks")) {
            // pegar a autenticação (usuário e senha)
            var authorization = request.getHeader("Authorization");

            // Pegar apenas a senha criptografada e removendo o Basic
            var authEncoded = authorization.substring("Basic".length()).trim();
            // Transformando a senha em bit
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            // Transformando a senha de bit para String
            var authString = new String(authDecode);
            // Desmembrando a string em duas partes que são o usuário e senha
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // Validar usuário
            var user = userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {
                // Validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
