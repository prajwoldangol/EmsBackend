package com.prajwol.service;
import com.prajwol.dto.EmsEmployerRequestDto;
import com.prajwol.dto.EmsEmployerResponseDto;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.entity.EmsRole;
import com.prajwol.repository.EmsEmployerRepo;
import com.prajwol.userservice.IdObfuscationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmsEmployerServiceImplTest {

    @Autowired
    private EmsEmployerRepo emsEmployerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdObfuscationService idObfuscationService;

    private EmsEmployerServiceImpl emsEmployerServiceImpl;

    @BeforeEach
    void setUp() {
        emsEmployerServiceImpl = new EmsEmployerServiceImpl(
                null, emsEmployerRepo, passwordEncoder, null, null, null, idObfuscationService);
    }

    @Test
    void testRegisterEmployer() {
        EmsEmployerRequestDto requestDto = new EmsEmployerRequestDto();
        requestDto.setPassword("password");
        requestDto.setUsername("username");
        requestDto.setCompanyName("companyName");
        requestDto.setPhone("1234567890");

        EmsEmployerResponseDto responseDto = emsEmployerServiceImpl.registerEmployer(requestDto);

        assertNotNull(responseDto);
        assertEquals("username", responseDto.getUsername());
        assertTrue(emsEmployerRepo.findById(idObfuscationService.idMask().unmask(responseDto.getId())).isPresent());
    }

    @ParameterizedTest
    @CsvSource({
            "1, username1, companyName1, 1234567890",
            "2, username2, companyName2, 0987654321"
    })
    void testGetEmployerbyId(String id, String username, String companyName, String phone) {
        EmsEmployer employer = EmsEmployer.builder()
                .id(Long.parseLong(id))
                .username(username)
                .password(passwordEncoder.encode("password"))
                .companyName(companyName)
                .phone(phone)
                .signedUpDate(Instant.now())
                .role(EmsRole.USER)
                .build();

        emsEmployerRepo.save(employer);

        Optional<EmsEmployerResponseDto> result = emsEmployerServiceImpl.getEmployerbyId(idObfuscationService.idMask().mask(Long.parseLong(id)));

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "2"
    })
    void testDeleteEmployer(String id) {
        EmsEmployer employer = EmsEmployer.builder()
                .id(Long.parseLong(id))
                .username("username")
                .password(passwordEncoder.encode("password"))
                .companyName("companyName")
                .phone("1234567890")
                .signedUpDate(Instant.now())
                .role(EmsRole.USER)
                .build();

        emsEmployerRepo.save(employer);

        emsEmployerServiceImpl.deleteEmployer(idObfuscationService.idMask().mask(Long.parseLong(id)));

        assertTrue(emsEmployerRepo.findById(Long.parseLong(id)).isEmpty());
    }
}
