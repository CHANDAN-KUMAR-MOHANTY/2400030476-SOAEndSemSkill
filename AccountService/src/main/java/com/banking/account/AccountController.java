
package com.banking.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping({"/internal/accounts", "/api/v1/internal/accounts"})
public class AccountController {

    @GetMapping({"", "/"})
    public ResponseEntity<Map<String, Object>> getAccounts(HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("service", "Account Service");
        response.put("status", "SUCCESS");
        response.put("port", request.getServerPort());
        response.put("receivedPath", request.getRequestURI());
        response.put("message", "Account Service: Request received at " + request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<Map<String, Object>> getAccountById(
            @PathVariable String id, HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("service", "Account Service");
        response.put("status", "SUCCESS");
        response.put("port", request.getServerPort());
        response.put("receivedPath", request.getRequestURI());
        response.put("accountId", id);
        response.put("message", "Account Service: Request received for ID " + id + " at " + request.getRequestURI());
        return ResponseEntity.ok(response);
    }
}
