package unit.nft.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.common.dto.request.AirdropRequestMessage;
import com.game.common.exception.BusinessException;
import com.game.common.repository.db.AirdropRepository;
import com.game.common.repository.db.MemberRepository;
import com.game.common.repository.redis.AirdropRequestRedisRepository;
import com.game.game.nft.service.AirdropService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.anyLong;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AirdropServiceTest {


    @InjectMocks
    private AirdropService airdropService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private AirdropRequestRedisRepository airdropRequestRedisRepository;

    @Mock
    private ObjectMapper objectMapper ;

    @Mock
    private AirdropRepository airdropRepository;

    @Mock
    private MemberRepository memberRepository;

    /*이 코드는 AirdropService 클래스의 validateAirdropCount 메서드를 테스트하는 코드입니다.
        특히, 테스트는 AirdropRequestRedisRepository에서 반환된 값이 maxCount보다 큰 경우에 BusinessException이 발생하는지 확인합니다
        테스트는 목 객체를 사용하여 리포지토리의 동작을 제어하고, 예외가 발생하는지를 검증합니다.*/
    @DisplayName("에어드랍 신청시 에어드랍 신청 횟수 검증")
    @Test
    void testValidateAirdropCount() {

        AirdropRequestMessage airdropRequestMessage = AirdropRequestMessage.builder().airdropId(1L).memberId(1L).build();
        Long maxCount = 10L;

        AirdropRequestRedisRepository airdropRequestRedisRepository = mock(AirdropRequestRedisRepository.class);
        when(airdropRequestRedisRepository.getAirdropSize(anyLong())).thenReturn(11L);  // Mock the response for getAirdropSize

        AirdropService validator = new AirdropService(rabbitTemplate, airdropRequestRedisRepository, objectMapper, airdropRepository, memberRepository);

        // Act and Assert
        Assertions.assertThrows(BusinessException.class, () -> {
            validator.validateAirdropCount(airdropRequestMessage, maxCount);
        });


    }



}
