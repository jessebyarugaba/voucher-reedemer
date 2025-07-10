package com.example.voucher.controller;

import com.example.voucher.model.Voucher;
import com.example.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @PostMapping("/redeem")
    public Map<String, Object> redeemVoucher(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");

        if (code == null) {
            return Map.of("success", false, "message", "Code is required");
        }

        System.out.println("Received code: " + code);
    

        var voucherOptional = voucherService.redeemVoucher(code);

        if (voucherOptional.isEmpty()) {
            return Map.of("success", false, "message", "Invalid or already used voucher");
        }

        Voucher voucher = voucherOptional.get();
        return Map.of(
                "success", true,
                "packageType", voucher.getPackageType(),
                "expirationDate", voucher.getExpirationDate());
    }

}
