package com.alamin.eshoppers.controller;

import com.alamin.eshoppers.dto.UserDto;
import com.alamin.eshoppers.repository.UserRepositoryImpl;
import com.alamin.eshoppers.service.UserService;
import com.alamin.eshoppers.service.UserServiceImpl;
import com.alamin.eshoppers.utils.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final static Logger LOGGER = LoggerFactory.getLogger(SignupServlet.class);

    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Serving signup page");
        req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = copyParametersTo(req);

        var errors = ValidationUtil.getInstance().validate(userDto);

        if (!errors.isEmpty()) {
            LOGGER.info("user send invalid data {}", userDto);

            req.setAttribute("userDto", userDto);
            req.setAttribute("errors", errors);

            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
        } else if (userService.isNotUniqueUserName(userDto)) {
            LOGGER.info("Username: {} is already exists", userDto.getUsername());

            errors.put("username", "already taken, please use different one");

            req.setAttribute("errors", errors);
            req.setAttribute("userDto", userDto);

            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
        } else if (userService.isNotUniqueEmail(userDto)) {
            LOGGER.info("Email: {} is already exists", userDto.getEmail());

            errors.put("email", "already taken, please use different one");

            req.setAttribute("errors", errors);
            req.setAttribute("userDto", userDto);

            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
        } else {
            LOGGER.info("User is valid. creating a new user with {}", userDto);

            userService.saveUser(userDto);
            resp.sendRedirect("/login");
        }
    }

    private UserDto copyParametersTo(HttpServletRequest req) {
        var userDto = new UserDto();
        userDto.setFirstName(req.getParameter("firstName"));
        userDto.setLastName(req.getParameter("lastName"));
        userDto.setPassword(req.getParameter("password"));
        userDto.setPasswordConfirmed(req.getParameter("passwordConfirmed"));
        userDto.setEmail(req.getParameter("email"));
        userDto.setUsername(req.getParameter("username"));

        return userDto;
    }

}
