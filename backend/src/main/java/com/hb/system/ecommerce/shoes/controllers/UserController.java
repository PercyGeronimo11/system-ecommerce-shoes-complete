package com.hb.system.ecommerce.shoes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.system.ecommerce.shoes.dto.ApiResponse;
import com.hb.system.ecommerce.shoes.dto.request.UserEditReq;
import com.hb.system.ecommerce.shoes.entity.User;
import com.hb.system.ecommerce.shoes.services.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> list() {
        List<User> users = userService.listAll();
        ApiResponse<List<User>> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Lista de usuarios exitosamente");
        response.setData(users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> delete(@PathVariable int id) {
        userService.delete(id);
        ApiResponse<User> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Producto eliminado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> edit(@PathVariable int id, @RequestBody UserEditReq userRequest) {
        User user = userService.update(id, userRequest);
        user.setStatus(true);
        ApiResponse<User> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("El usuario se actualizó exitosamente");
        response.setData(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*
     * @GetMapping("/delete/{id}")
     * public String showDeleteConfirm(@PathVariable("id") Integer id, Model model)
     * {
     * User user = userService.getUserById(id);
     * model.addAttribute("user", user);
     * return "confirm-delete";
     * }
     * 
     * @PostMapping("/delete/{id}")
     * public String deleteUser(@PathVariable("id") Integer id) {
     * userService.deleteUser(id);
     * return "redirect:/user/list";
     * }
     */

}
