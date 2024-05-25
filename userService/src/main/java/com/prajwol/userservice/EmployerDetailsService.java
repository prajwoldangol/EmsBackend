package com.prajwol.userservice;

import com.prajwol.config.CustomEmployerDetails;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.repository.EmsEmployerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployerDetailsService  implements UserDetailsService {

    private EmsEmployerRepo emsEmployerRepo;
    @Autowired
    public EmployerDetailsService(EmsEmployerRepo emsEmployerRepo) {
        this.emsEmployerRepo = emsEmployerRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmsEmployer emp =  emsEmployerRepo.findByUsername(username).orElseThrow();
        UserDetails userDetails = new CustomEmployerDetails(emp);
        return userDetails;
    }
}
