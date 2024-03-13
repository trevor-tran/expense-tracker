package com.trevortran.expensetracker.controller;

import com.trevortran.expensetracker.model.Transaction;
import com.trevortran.expensetracker.service.CategoryService;
import com.trevortran.expensetracker.service.TransactionService;
import com.trevortran.expensetracker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ModelAndView getAllTransactions() {
        ModelAndView modelAndView = new ModelAndView("expense");
        modelAndView.addObject("transactions", transactionService.findAll());
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }



    @PostMapping("/")
    public RedirectView saveTransaction(@ModelAttribute Transaction transaction) {
        transactionService.save(transaction);

        return new RedirectView("/transaction");
    }

    @GetMapping("/analytic")
    public ModelAndView renderAnalytic() {
        ModelAndView modelAndView = new ModelAndView("analytic");
        modelAndView.addObject("transactions", transactionService.findAll());
        return modelAndView;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable("transactionId") UUID transactionId) {
        Optional<Transaction> transaction = transactionService.findById(transactionId);
        return transaction.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transactionId") UUID transactionId) {
        boolean exist = transactionService.existsById(transactionId);
        if (!exist) {
            return ResponseEntity.badRequest().build();
        }

        transactionService.delete(transactionId);

        return ResponseEntity.ok().build();
    }
}
