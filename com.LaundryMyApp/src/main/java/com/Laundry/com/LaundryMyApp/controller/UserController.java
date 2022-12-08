package com.Laundry.com.LaundryMyApp.controller;

import java.util.List;

import javax.validation.Valid;

import com.Laundry.com.LaundryMyApp.model.Role;
import com.Laundry.com.LaundryMyApp.model.UserLaundry;
import com.Laundry.com.LaundryMyApp.service.RoleService;
import com.Laundry.com.LaundryMyApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/userLaundry")
public class UserController {
    /*
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    */
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;


    /**
     * method to verify what is the role of the user
     * */
    private boolean isAuthorized(UserLaundry userLaundry, String role) {
        for (Role rl : userLaundry.getRoles()) {
            if (rl.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/index")
    public String index(@CurrentSecurityContext(expression="authentication.name")
                        String login) {

        UserLaundry userLaundry = userService.searchUserByLogin(login);

        String redirectURL = "";
        if (isAuthorized(userLaundry, "ADMIN")) {
            redirectURL = "/auth/admin/admin-index";
        } else if (isAuthorized(userLaundry, "USER")) {
            redirectURL = "/auth/user/user-index";
        } else if (isAuthorized(userLaundry, "BIBLIOTECARIO")) {
            redirectURL = "/auth/biblio/biblio-index";
        }
        return redirectURL;
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new UserLaundry());
        return "/publica-criar-usuario";
    }

    @PostMapping("/save")
    public String saveUser(@Valid UserLaundry userLaundry, BindingResult result,
                           Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/publica-criar-usuario";
        }

        UserLaundry usr = userService.searchUserByLogin(userLaundry.getLogin());
        if (usr != null) {
            model.addAttribute("Found Login", "Login is already exist");
            return "/publica-criar-usuario";
        }

        userService.saveUser(userLaundry);
        attributes.addFlashAttribute("message", "User added!");
        return "redirect:/userLaundry/new";
    }

    @RequestMapping("/admin/listar")
    public String userList(Model model) {
        List<UserLaundry> list = userService.userList();
        model.addAttribute("users", list);
        return "/auth/admin/admin-listar-usuario";
    }

    @GetMapping("/admin/apagar/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteUserId(id);
        return "redirect:/userLaundry/admin/list";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        UserLaundry userLaundry = userService.searchUserById(id);
        model.addAttribute("user", userLaundry);
        return "/auth/user/user-alterar-usuario";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id,
                           @Valid UserLaundry userLaundry, BindingResult result) {
        if (result.hasErrors()) {
            userLaundry.setId(id);
            return "/auth/user/user-alterar-usuario";
        }
        userService.altUser(userLaundry);
        return "redirect:/userLaundry/admin/list";
    }

    @GetMapping("/editRole/{id}")
    public String roleSelect(@PathVariable("id") long id, Model model) {
        UserLaundry userLaundry = userService.searchUserById(id);
        model.addAttribute("user", userLaundry);
        List<Role> roles = roleService.roleList();
        model.addAttribute("rolelust", roles);

        return "/auth/admin/admin-editar-papel-usuario";
    }

    @PostMapping("/editRole/{id}")
    public String roleAssigned(@PathVariable("id") long idUser,
                               @RequestParam(value = "pps", required=false) int[] pps,
                               UserLaundry userLaundry,
                               RedirectAttributes attributes) {
        if (pps == null) {
            userLaundry.setId(idUser);
            attributes.addFlashAttribute("message", "At least one role need to be assigned.");
            return "redirect:/userLaundry/editRole/"+idUser;
        } else {
            userService.assignRoleToUser(idUser, pps, userLaundry.isEnable());
        }
        return "redirect:/userLaundry/admin/list";
    }
}
