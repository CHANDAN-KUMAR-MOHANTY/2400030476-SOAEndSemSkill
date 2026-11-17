package com.banking.account.controller;

import com.banking.account.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping({"/api/v1/internal/accounts", "/internal/accounts"})
public class AccountController {

    // In-memory account store
    private final Map<String, Account> accountStore = new LinkedHashMap<>();

    public AccountController() {
        // Pre-populate with sample banking accounts
        accountStore.put("ACC001", new Account("ACC001", "Alice Johnson", "SAVINGS", 25000.00));
        accountStore.put("ACC002", new Account("ACC002", "Bob Smith", "CURRENT", 180000.50));
        accountStore.put("ACC003", new Account("ACC003", "Charlie Brown", "SAVINGS", 5200.75));
    }

    /**
     * GET /api/v1/internal/accounts or /internal/accounts
     * Returns all accounts + echoes the received request path for verification.
     */
    @GetMapping({"", "/"})
    public ResponseEntity<Map<String, Object>> getAllAccounts(HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("service", "account-service");
        response.put("port", request.getServerPort());
        response.put("receivedPath", request.getRequestURI());
        response.put("message", "Internal Account API responded successfully");
        response.put("accounts", new ArrayList<>(accountStore.values()));
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/v1/internal/accounts/{id} or /internal/accounts/{id}
     * Returns a single account by ID.
     */
    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<Map<String, Object>> getAccountById(
            @PathVariable String id, HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("service", "account-service");
        response.put("port", request.getServerPort());
        response.put("receivedPath", request.getRequestURI());

        Account account = accountStore.get(id);
        if (account != null) {
            response.put("account", account);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Account not found: " + id);
            return ResponseEntity.status(404).body(response);
        }
    }
}
