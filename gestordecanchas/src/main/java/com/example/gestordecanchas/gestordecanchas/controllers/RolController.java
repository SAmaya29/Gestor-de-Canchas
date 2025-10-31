package com.example.gestordecanchas.gestordecanchas.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.gestordecanchas.gestordecanchas.DTO.DTORolResponse;
import com.example.gestordecanchas.gestordecanchas.service.RolService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/rol")
public class RolController {

    @Autowired
    RolService rolService;

    @GetMapping("/roles")
    public ResponseEntity<List<DTORolResponse>> obtenerRoles() {
        try {
            List<DTORolResponse> roles = rolService.obtenerRoles();
            return ResponseEntity.ok(roles);
        }  catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
