package com.hb.system.ecommerce.shoes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.hb.system.ecommerce.shoes.entity.Customer;
import com.hb.system.ecommerce.shoes.entity.User;
import com.hb.system.ecommerce.shoes.repositories.CustomerRepository;
import com.hb.system.ecommerce.shoes.repositories.RolRepository;
import com.hb.system.ecommerce.shoes.repositories.UserRepository;
import com.hb.system.ecommerce.shoes.exceptions.CustomException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public List<Customer> listAll() {

        return customerRepository.findAll();
    }

    public Customer getById(int id) {
        Optional<Customer> customerFind = customerRepository.findById(id);
        return customerFind.get();

    }

    public Customer logIn(String email, String password) {

        Customer customer = customerRepository.findByCustEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if (!passwordEncoder.matches(password, customer.getCustPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return customer;
    }

    public Customer save(Customer customer) {
        Optional<Customer> customerOpt = customerRepository.findByCustDni(customer.getCustDni());
        if (customerOpt.isPresent()) {
            throw new CustomException("El DNI del cliente ya existe");
        }
        Optional<User> usuarioOpt = userRepository.findByUsername(customer.getCustEmail());
        if (usuarioOpt.isPresent()) {
            throw new CustomException("El Correo del cliente ya existe");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(customer.getCustPassword());
        User usuar = new User();
        usuar.setName(customer.getCustFirstName());
        usuar.setUsername(customer.getCustEmail());
        usuar.setPassword(encryptedPassword);
        usuar.setRegisterDate(LocalDateTime.now());
        usuar.setRole(rolRepository.findById(3).get());
        usuar.setStatus(true);
        usuar = userRepository.save(usuar);
        customer.setCustFirstName(customer.getCustFirstName());
        customer.setCustLastName(customer.getCustLastName());
        customer.setCustDni(customer.getCustDni());
        customer.setCustDepartment(customer.getCustDepartment());
        customer.setCustBirthDate(customer.getCustBirthDate());
        customer.setCustCity(customer.getCustCity());
        customer.setCustProvince(customer.getCustProvince());
        customer.setCustPassword(encryptedPassword);
        customer.setCustCellphone(customer.getCustCellphone());
        customer.setCustStatus(true);
        customer.setUsuario(usuar);
        return customerRepository.save(customer);
    }

    // Método para actualizar un cliente
    public Customer update(int idActual, Customer customerActualizado) {
        if (customerRepository.existsById(idActual)) {
            customerActualizado.setId(idActual);

            User usuarioActualizado = customerActualizado.getUsuario();
            Optional<User> usuarioOpt = userRepository.findById(usuarioActualizado.getId());

            if (usuarioOpt.isPresent()) {
                User usuarioExistente = usuarioOpt.get();
                usuarioExistente.setRole(usuarioActualizado.getRole());

                User usuarioGuardado = userRepository.save(usuarioExistente);
                customerActualizado.setUsuario(usuarioGuardado);

                return customerRepository.save(customerActualizado);
            } else {
                throw new RuntimeException("Usuario no encontrado");
            }
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
    }

    // Método para eliminar (desactivar) una catg
    public void delete(int idcliente) {
        Optional<Customer> categoria = customerRepository.findById(idcliente);
        categoria.get().setCustStatus(false);
        customerRepository.save(categoria.get());
    }
}
