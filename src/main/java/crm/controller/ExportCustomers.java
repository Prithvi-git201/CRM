package crm.controller;

import crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExportCustomers {

    @Autowired
    CustomerService customerService;

    /**
     * Handle request to export customers to CSV
     */
    @GetMapping("/exportCustomersToCsv")
    public String exportCustomersToCsv(Model model) {
        model.addAttribute("customers", customerService.listAllCustomers());
        return "csvView";
    }

}
