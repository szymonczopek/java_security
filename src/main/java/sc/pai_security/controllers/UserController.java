package sc.pai_security.controllers;
import sc.pai_security.dao.UserDao;
import sc.pai_security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;


@Controller
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao dao;

    @GetMapping("/login")
    public String loginPage() {
        //zwrócenie nazwy widoku logowania - login.html
        return "login";
    }
    @GetMapping("/register")
    public String registerPage(Model m) {

        //dodanie do modelu nowego użytkownika
        m.addAttribute("user", new User());
        //zwrócenie nazwy widoku rejestracji - register.html
        return "register";
    }
    @PostMapping("/register")
    public String registerPagePOST(@Valid User user, BindingResult binding) {


        if (dao.findByLogin(user.getLogin()) != null) {
            binding.rejectValue("login", "error.users", "Istnieje już użytkownik o takim loginie");
        }
        if (binding.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
        //przekierowanie do adresu url: /login
        return "redirect:/login";
    }
    @GetMapping("/profile")
    public String profilePage(Model m, Principal principal) {
        //dodanie do modelu aktualnie zalogowanego użytkownika:
        m.addAttribute("user", dao.findByLogin(principal.getName()));
        //zwrócenie nazwy widoku profilu użytkownika - profile.html
        return "profile";
    }
    @GetMapping("/users")
    public String usersList(Model m) {
        //definicja metody, która zwróci do widoku users.html listę
        //użytkowników z bd
            m.addAttribute("lista",dao.findAll());
        return "users";

    }

    @GetMapping("/delete")
    public String delete(Principal principal){
       dao.delete(dao.findByLogin(principal.getName()));
       return "redirect:/logout";
    }

    @GetMapping("/edit")
    public String showEditForm(Principal principal, Model model){
        model.addAttribute("user",dao.findByLogin(principal.getName()));
        return "editUser";
    }
    @PostMapping("/update")
    public String update(@Valid User user, BindingResult bindingResult, Principal principal){
//        model.addAttribute("user", dao.findByLogin(principal.getName()));
//        User currentUser = dao.findByLogin(principal.getName());
        if(dao.findByLogin(user.getLogin()) != null && !Objects.equals(user.getLogin(), principal.getName())){
            bindingResult.rejectValue("login", "error.users", "Istnieje już użytkownik o podanym loginie!");
        }
        if(bindingResult.hasErrors()){
            return "editUser";
        }
        user.setName(user.getName());
        user.setSurname(user.getSurname());
        user.setLogin(user.getLogin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
        return "redirect:/logout";
    }
}