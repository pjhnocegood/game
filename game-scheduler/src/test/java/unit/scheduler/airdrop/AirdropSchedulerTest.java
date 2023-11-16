package unit.scheduler.airdrop;

import com.game.common.entity.db.Airdrop;
import com.game.common.entity.db.AirdropRequestMember;
import com.game.scheduler.airdrop.scheduler.AirdropScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class AirdropSchedulerTest {

    @InjectMocks
    private AirdropScheduler airdropScheduler;


/*    이 코드는 airdropScheduler 객체의 airdropRequestMemberListToMap 메서드를 테스트합니다.
    테스트는 두 개의 AirdropRequestMember 객체를 생성하고, 이를 리스트에 추가한 후 airdropScheduler의 메서드에 전달하여 결과를 확인합니다.
    메서드는 AirdropRequestMember 객체를 airdropId 단위로 매핑하며, 테스트는 이 매핑이 정확하게 이루어지는지 검증합니다.*/
    @DisplayName("airdropId 단위로 Map에 AirdropRequestMember를 List로 저장하는 로직 테스트 ")
    @Test
    void testAirdropRequestMemberListToMap() {
        // Arrange
        AirdropRequestMember airdropRequestMember1 = createAirdropRequestMember(1L);
        AirdropRequestMember airdropRequestMember2 = createAirdropRequestMember(2L);

        List<AirdropRequestMember> airdropRequestMemberList = new ArrayList<>();
        airdropRequestMemberList.add(airdropRequestMember1);
        airdropRequestMemberList.add(airdropRequestMember2);

        // Act
        Map<Long, List<AirdropRequestMember>> result = airdropScheduler.airdropRequestMemberListToMap(airdropRequestMemberList);

        // Assert

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(List.of(airdropRequestMember1), result.get(1L));
        Assertions.assertEquals(List.of(airdropRequestMember2), result.get(2L));
    }

    private AirdropRequestMember createAirdropRequestMember(Long airdropId) {
        AirdropRequestMember airdropRequestMember = mock(AirdropRequestMember.class);
        Airdrop airdrop = mock(Airdrop.class);
        when(airdrop.getAirdropId()).thenReturn(airdropId);
        when(airdropRequestMember.getAirdrop()).thenReturn(airdrop);
        return airdropRequestMember;
    }
}
