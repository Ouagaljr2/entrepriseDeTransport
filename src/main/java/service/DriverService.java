package service;

import java.util.List;

import org.springframework.stereotype.Service;

import model.Driver;
import repository.DriverRepository;

//Services
@Service
public class DriverService {
 private final DriverRepository driverRepository;

 public DriverService(DriverRepository driverRepository) {
     this.driverRepository = driverRepository;
 }

 public List<Driver> findAllDrivers() {
     return driverRepository.findAll();
 }

 public Driver saveDriver(Driver driver) {
     return driverRepository.save(driver);
 }

 public Driver updateDriver(Long id, Driver updatedDriver) {
     return driverRepository.findById(id).map(driver -> {
         driver.setFirstName(updatedDriver.getFirstName());
         driver.setLicenseNumber(updatedDriver.getLicenseNumber());
         driver.setPhoneNumber(updatedDriver.getPhoneNumber());
         return driverRepository.save(driver);
     }).orElseThrow(() -> new RuntimeException("Driver not found"));
 }

 public void deleteDriver(Long id) {
     driverRepository.deleteById(id);
 }

 public List<Driver> searchDrivers(String name) {
     return driverRepository.findByNameContaining(name);
 }
}