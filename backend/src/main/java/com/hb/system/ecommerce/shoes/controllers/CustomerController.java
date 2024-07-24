package com.hb.system.ecommerce.shoes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hb.system.ecommerce.shoes.dto.request.LoginReq;
import com.hb.system.ecommerce.shoes.dto.ApiResponse;
import com.hb.system.ecommerce.shoes.entity.Customer;
import com.hb.system.ecommerce.shoes.exceptions.CustomException;
import com.hb.system.ecommerce.shoes.services.CustomerService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private  CustomerService customerService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> list() {
        List<Customer> customers = customerService.listAll();
        ApiResponse<List<Customer>> response= new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Lista de clientes exitosamente");
        response.setData(customers);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> create(@RequestBody Customer request) {
        ApiResponse<Customer> response = new ApiResponse<>();
        try {
            Customer customer = customerService.save(request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Se registró un cliente exitosamente");
            response.setData(customer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Customer>> logIn(@RequestBody LoginReq loginRequest) {
        try {
            Customer client = customerService.logIn(loginRequest.getEmail(), loginRequest.getPassword());
            ApiResponse<Customer> response = new ApiResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Login exitoso");
            response.setData(client);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<Customer> response = new ApiResponse<>();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Email o contraseña incorrectos");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> edit(@PathVariable int id, @RequestBody Customer customerRequest){
        Customer customer= customerService.update(id,customerRequest);
        ApiResponse<Customer> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("el cliente se actualizó exitosamente");
        response.setData(customer);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getById(@PathVariable int id){
        Customer customer= customerService.getById(id);
        ApiResponse<Customer> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Detalle del customer recuperado exitossamente");
        response.setData(customer);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> delete(@PathVariable int id){
        customerService.delete(id);
        ApiResponse<Customer> response=new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("customer eliminada exitosamente");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
