package org.aap.booking.service;

import org.aap.booking.dto.PaymentStatus;
import org.aap.booking.entity.Booking;
import org.aap.booking.entity.Payment;
import org.aap.booking.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment initiatePayment(Booking booking) {
        // Dummy payment step
        Payment payment = new Payment(booking, booking.getTotalAmount());
        payment.setStatus(PaymentStatus.INITIATED);
        payment.setReference("PAY-" + UUID.randomUUID());
        payment = paymentRepository.save(payment);

        // Simulate payment success
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        return payment;
    }

}

