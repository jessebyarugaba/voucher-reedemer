package com.example.voucher.service;

import com.example.voucher.model.Voucher;
import com.example.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Transactional
    public Optional<Voucher> redeemVoucher(String code) {
        Optional<Voucher> optionalVoucher = voucherRepository.findByCode(code);

        if (optionalVoucher.isEmpty()) {
            return Optional.empty();
        }

        Voucher voucher = optionalVoucher.get();

        if (voucher.isUsed()) {
            return Optional.empty();
        }

        voucher.setUsed(true);
        voucherRepository.save(voucher);
        voucherRepository.flush(); // <- force Hibernate to flush update
        return Optional.of(voucher);

    }
}
