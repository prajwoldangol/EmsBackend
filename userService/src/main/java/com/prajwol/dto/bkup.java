//package com.prajwol.dto;
//
//import java.util.Optional;
//import java.util.function.Consumer;
//import java.util.function.Predicate;
//
//public class EmployeeServiceBkup {
//
//    private final EmsEmployeeRepo emsEmployeeRepo;
//    private final PasswordEncoder passwordEncoder;
//    private final EmsDepartmentRepo emsDepartmentRepo;
//    private final IdMask idMask;
//
//    public EmployeeService(EmsEmployeeRepo emsEmployeeRepo, PasswordEncoder passwordEncoder, EmsDepartmentRepo emsDepartmentRepo, IdMask idMask) {
//        this.emsEmployeeRepo = emsEmployeeRepo;
//        this.passwordEncoder = passwordEncoder;
//        this.emsDepartmentRepo = emsDepartmentRepo;
//        this.idMask = idMask;
//    }
//
//    public static final Predicate<String> NOT_EMPTY_STRING = s -> s != null && !s.isEmpty();
//
//    private static <T> void updateFieldIfPresent(T value, Consumer<T> updater, Predicate<T> condition) {
//        if (condition.test(value)) {
//            updater.accept(value);
//        }
//    }
//
//    public EmsEmployee updateEmployee(Long id, EmsEmployeeDto em) {
//        Optional<EmsEmployee> optionalEmployee = emsEmployeeRepo.findById(id);
//        if (optionalEmployee.isEmpty()) {
//            throw new EmsCustomException("Employee not found", "404");
//        }
//
//        EmsEmployee employee = optionalEmployee.get();
//
//        // Update fields if they are provided using the utility method with the reusable predicate
//        updateFieldIfPresent(em.getUsername(), employee::setUsername, NOT_EMPTY_STRING);
//        updateFieldIfPresent(em.getPassword(), pwd -> employee.setPassword(passwordEncoder.encode(pwd)), NOT_EMPTY_STRING);
//        updateFieldIfPresent(em.getPhone(), employee::setPhone, NOT_EMPTY_STRING);
//        updateFieldIfPresent(em.getRole(), role -> employee.setRole(EmsRole.valueOf(role)), NOT_EMPTY_STRING);
//        updateFieldIfPresent(em.getDepartmentId(), deptId -> {
//            Long departmentId = idMask.unmask(deptId);
//            Optional<EmsDepartment> departmentById = emsDepartmentRepo.findById(departmentId);
//            departmentById.ifPresent(employee::setEmsDepartment);
//        }, NOT_EMPTY_STRING);
//
//        // Save the updated employee
//        EmsEmployee updatedEmployee = emsEmployeeRepo.save(employee);
//        log.info("Employee updated successfully");
//        return updatedEmployee;
//    }
//}
