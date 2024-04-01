package com.pathology.controller;

import com.pathology.dao.AppointmentDao;
import com.pathology.model.AppointmentUser;
import com.pathology.model.ContactUsUser;
import com.pathology.model.PathologyLogin;
import com.pathology.model.RegistrationUser;
import com.pathology.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRegistrationService userRegistrationService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("pathologyLogin",new PathologyLogin());
        return "login";
    }
    @PostMapping("/pathology")
    public ModelAndView createUser(@ModelAttribute("pathologyLogin") PathologyLogin pathologyLogin, HttpServletRequest request) {
        ModelAndView modelAndViewObj = new ModelAndView();
        if(userRegistrationService.findByEmailAndPassword(pathologyLogin.getEmailId(),pathologyLogin.getPassword())){
            modelAndViewObj.addObject("message", "Login Successfully");
            HttpSession session = request.getSession();
            session.setAttribute("emailid",pathologyLogin.getEmailId());
            session.setAttribute("username", userRegistrationService.getUserByGmail(pathologyLogin.getEmailId()));
            modelAndViewObj.setViewName("index");
        }else {
            modelAndViewObj.addObject("message", "Invalid Credentials");
            modelAndViewObj.setViewName("login");
        }
        return modelAndViewObj ;
    }

    @GetMapping("/logout")
    public String logout(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        model.addAttribute("pathologyLogin",new PathologyLogin());
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistration(Model model) {
        model.addAttribute("registrationUser",new RegistrationUser());
        return "registration";
    }
    @PostMapping("/registerUser")
    public ModelAndView createRegisterUser(@ModelAttribute("registrationUser") RegistrationUser registrationUser) {
         ModelAndView modelAndViewObj = new ModelAndView();
         String msg = userRegistrationService.registerUser(registrationUser);
        if (msg.equals("success")) {
            modelAndViewObj.addObject("message", "Registration Successful. Please Login");
            modelAndViewObj.setViewName("registration");
        } else if(msg.equals("already")) {
            modelAndViewObj.addObject("error", "Already Register user or EmailId");
            modelAndViewObj.setViewName("registration");
        }else {
            modelAndViewObj.addObject("error", "Something technical happen.. please try again in sometime");
            modelAndViewObj.setViewName("registration");
        }
        return modelAndViewObj;
    }
    @GetMapping("/contact")
    public String showContact(Model model) {
        model.addAttribute("contactUsUser",new ContactUsUser());
        return "contact";
    }
    @PostMapping("/contactUsEnquiry")
    public ModelAndView createContactUser(@ModelAttribute("contactUsUser") ContactUsUser contactUsUser){
        ModelAndView modelAndViewObj = new ModelAndView();
        String msg = userRegistrationService.contactUsUser(contactUsUser);
        if (msg.equals("true")) {
            modelAndViewObj.addObject("message", "Your Enquiry is register we will get back to you");
            modelAndViewObj.setViewName("contact");
        }

        return modelAndViewObj;
    }
    @GetMapping("/appointment")
    public String showAppointment(Model model) {
        model.addAttribute("appointmentUser",new AppointmentUser());
        return "appointment";
    }
    @PostMapping("/appointmentEnquiry")
    public ModelAndView createAppointmentEnquiry(@ModelAttribute("appointmentUser") AppointmentUser appointmentUser) {
        ModelAndView modelAndViewObj = new ModelAndView();
        String msg = userRegistrationService.appointmentUser(appointmentUser);
        if (msg.equals("true")) {
            modelAndViewObj.addObject("message", "Your appointment is register we will get back to you");
            modelAndViewObj.setViewName("appointment");
        } else if(msg.equals("already")) {
            modelAndViewObj.addObject("message", "Your appointment is already register for same date and time");
            modelAndViewObj.setViewName("appointment");
        }
        return modelAndViewObj;
    }
    @GetMapping("/dashboard")
    public String showDashboard(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailid = (String) session.getAttribute("emailid");
        if (emailid != null) {
            List<AppointmentUser> appointments = userRegistrationService.getUserAppointment(emailid);
            model.addAttribute("appointments", appointments);
            System.out.println("emailid: " + emailid);
        } else {
            System.out.println("Username is not available in the session.");
        }
        return "dashboard";
    }

}
