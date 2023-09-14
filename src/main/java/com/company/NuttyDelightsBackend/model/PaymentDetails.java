package com.company.NuttyDelightsBackend.model;


import com.company.NuttyDelightsBackend.domain.PaymentMethod;
import com.company.NuttyDelightsBackend.domain.PaymentStatus;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "Payment_Details")
public class PaymentDetails {

    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId​;

}
