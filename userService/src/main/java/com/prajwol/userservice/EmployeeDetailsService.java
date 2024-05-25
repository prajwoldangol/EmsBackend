package com.prajwol.userservice;

import com.prajwol.config.CustomEmployeeDetails;
import com.prajwol.entity.EmsEmployee;
import com.prajwol.repository.EmsEmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailsService implements UserDetailsService {
    
    private EmsEmployeeRepo emsEmployeeRepo;
    @Autowired
    public EmployeeDetailsService(EmsEmployeeRepo emsEmployeeRepo) {
        this.emsEmployeeRepo = emsEmployeeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmsEmployee emsEmployee = emsEmployeeRepo.findByUsername(username).orElseThrow();
        CustomEmployeeDetails customEmployeeDetails = new CustomEmployeeDetails(emsEmployee);
        return customEmployeeDetails;
    }
}
