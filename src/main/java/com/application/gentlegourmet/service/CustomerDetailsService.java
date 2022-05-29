package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Customer;
import com.application.gentlegourmet.entity.CustomerUserDetails;
import com.application.gentlegourmet.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        if(customer == null) throw new UsernameNotFoundException("No Customer found with this username : " + username);

        return new CustomerUserDetails(customer);
    }

}
