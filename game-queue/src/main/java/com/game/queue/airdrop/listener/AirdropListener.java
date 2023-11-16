package com.game.queue.airdrop.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.common.dto.request.AirdropRequestMessage;
import com.game.queue.airdrop.dto.AirdropRequest;
import com.game.queue.airdrop.service.AirdropQueueService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirdropListener {

    private static final Logger log = LoggerFactory.getLogger(AirdropListener.class);

    private final ObjectMapper objectMapper ;

    private final AirdropQueueService airdropQueueService;


    @Value("${rabbitmq.airdrop.request.queue}")
    private String  airdropRequestQueue;

   @RabbitListener(queues = "airdrop.request.queue")
    public void airdropListener(String message)  {


       try {
           AirdropRequestMessage airdropRequestMessage = objectMapper.readValue(message , AirdropRequestMessage.class);
           airdropQueueService.requestAirdrop(airdropRequestMessage);

       } catch (Exception e) {
           log.error("airdropListener error : {}",e.getMessage());
       }

   }
}