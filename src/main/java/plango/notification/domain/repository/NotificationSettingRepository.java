package plango.notification.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plango.notification.domain.entity.NotificationSetting;

@Repository
public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {
}
