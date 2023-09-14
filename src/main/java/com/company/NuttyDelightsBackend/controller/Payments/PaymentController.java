package com.company.NuttyDelightsBackend.controller.Payments;


import com.company.NuttyDelightsBackend.domain.OrderStatus;
import com.company.NuttyDelightsBackend.domain.PaymentMethod;
import com.company.NuttyDelightsBackend.domain.PaymentStatus;
import com.company.NuttyDelightsBackend.dto.response.ApiResponse;
import com.company.NuttyDelightsBackend.dto.response.PayOnDeliveryResponse;
import com.company.NuttyDelightsBackend.dto.response.PaymentLinkResponse;
import com.company.NuttyDelightsBackend.dto.response.RemoveResponse;
import com.company.NuttyDelightsBackend.exception.OrderException;
import com.company.NuttyDelightsBackend.exception.UserException;
import com.company.NuttyDelightsBackend.model.Order;
import com.company.NuttyDelightsBackend.model.PaymentDetails;
import com.company.NuttyDelightsBackend.repository.OrderRepository;
import com.company.NuttyDelightsBackend.service.CartService;
import com.company.NuttyDelightsBackend.service.OrderService;
import com.company.NuttyDelightsBackend.service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/rzp_payment")
public class PaymentController {

    @Value("${razorpay.api.key}")
    String apiKey;
    @Value("${razorpay.api.secret}")
    String apiSecret;

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    static String RandGeneratedStr(int l)

    {

        // a list of characters to choose from in form of a string

        String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";

        // creating a StringBuffer size of AlphaNumericStr

        StringBuilder s = new StringBuilder(l);
        s.append("order_");

        int i;

        for ( i=0; i<l; i++) {

            //generating a random number using math.random()

            int ch = (int)(AlphaNumericStr.length() * Math.random());

            //adding Random character one by one at the end of s

            s.append(AlphaNumericStr.charAt(ch));

        }

        return s.toString();

    }

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable String orderId,
                                                                 @RequestHeader("Authorization")String jwt)
            throws RazorpayException, UserException, OrderException {

        Order order=orderService.findOrderById(orderId);
        try {
            // Instantiate a Razorpay client with your key ID and secret
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

            // Create a JSON object with the payment link request parameters
            JSONObject paymentLinkRequest = new JSONObject();
            //multiply by 100 to convert into rupees
            paymentLinkRequest.put("amount",order.getTotalPrice()* 100);
            paymentLinkRequest.put("currency","INR");

            // Create a JSON object with the customer details
            JSONObject customer = new JSONObject();
            customer.put("name",order.getUser().getFirstName()+" "+order.getUser().getLastName());
            customer.put("contact",order.getUser().getMobileNumber());
            customer.put("email",order.getUser().getEmail());
            paymentLinkRequest.put("customer",customer);

            // Create a JSON object with the notification settings
            JSONObject notify = new JSONObject();
//            notify.put("sms",true);
//            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            // Set the reminder settings
            paymentLinkRequest.put("reminder_enable",true);

            // Set the callback URL and method
            paymentLinkRequest.put("callback_url","https://nutty-delights.vercel.app/payment/"+orderId);
            paymentLinkRequest.put("callback_method","get");

            // Create the payment link using the paymentLink.create() method
            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res=new PaymentLinkResponse(paymentLinkUrl , paymentLinkId );

            PaymentLink fetchedPayment = razorpay.paymentLink.fetch(paymentLinkId);
            System.out.println("Fetched :" + fetchedPayment);
            System.out.println("Order :" + order);

            order.setOrderId(orderId);
            orderRepository.save(order);

            // Print the payment link ID and URL
            System.out.println("Payment link ID: " + paymentLinkId);
            System.out.println("Payment link URL: " + paymentLinkUrl);
            System.out.println("Order Id : "+fetchedPayment.get("order_id")+fetchedPayment);

            return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.ACCEPTED);

        } catch (RazorpayException e) {

            System.out.println("Error creating payment link: " + e.getMessage());
            throw new RazorpayException(e.getMessage());
        }

    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name="payment_id") String paymentId,
                                                @RequestParam("order_id")String orderId)
            throws RazorpayException, OrderException {


        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
        Order order =orderService.findOrderById(orderId);

        try {


            Payment payment = razorpay.payments.fetch(paymentId);
            System.out.println("payment details --- "+payment+payment.get("status"));

            if(payment.get("status").equals("captured")) {
                System.out.println("payment details --- "+payment+payment.get("status"));

                order.getPaymentDetails().setPaymentId(paymentId);
                if(payment.get("method").equals("upi")) {
                    order.getPaymentDetails().setPaymentMethod(PaymentMethod.UPI);
                }
                else if(payment.get("method").equals("card")) {
                    order.getPaymentDetails().setPaymentMethod(PaymentMethod.DEBIT_CARD);
                }
                else if(payment.get("method").equals("netbanking")) {
                    order.getPaymentDetails().setPaymentMethod(PaymentMethod.NET_BANKING);
                }
                else{
                    order.getPaymentDetails().setPaymentMethod(PaymentMethod.UPI);
                }
//                order.getPaymentDetails().setRazorpayPaymentLinkStatus();
                order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
                order.setOrderStatus(OrderStatus.PLACED);
                order.setOrderId(payment.get("order_id"));
                order.setCreatedAt(LocalDateTime.now().plusHours(5).plusMinutes(30));


                RemoveResponse res =   cartService.clearCart(order.getUser().getUserId());

                System.out.println(order.getPaymentDetails().getStatus()+"payment status ");
                orderRepository.save(order);
            }
            ApiResponse res=new ApiResponse("your order get placed", true);

            return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("error payment -------- ");
            new RedirectView("https://nutty-delights.vercel.app/payment/failed");
            throw new RazorpayException(e.getMessage());
        }

    }


    @PostMapping("/payments/pod/{orderId}")
    public ResponseEntity<PayOnDeliveryResponse> payOnDelivery(@PathVariable String orderId,
                                                               @RequestHeader("Authorization")String jwt)
            throws UserException, OrderException {

        Order order=orderService.findOrderById(orderId);

        try{

            order.setOrderStatus(OrderStatus.PLACED);
            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setPaymentMethod(PaymentMethod.PAY_ON_DELIVERY);
            paymentDetails.setStatus(PaymentStatus.PENDING);
            order.setPaymentDetails(paymentDetails);
            order.setCreatedAt(LocalDateTime.now());
            Random r = new Random( System.currentTimeMillis() );
            String orderID = PaymentController.RandGeneratedStr(13);
            order.setOrderId(orderID);

            PayOnDeliveryResponse payOnDeliveryResponse = new PayOnDeliveryResponse(false , "Order placed" , order);
            RemoveResponse res =   cartService.clearCart(order.getUser().getUserId());
            orderRepository.save(order);
            return new ResponseEntity<PayOnDeliveryResponse>(payOnDeliveryResponse, HttpStatus.ACCEPTED);

        } catch (Exception e) {

            System.out.println("Error creating payment link: " + e.getMessage());
            throw new OrderException(e.getMessage());
        }

    }


}
