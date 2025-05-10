package vn.com.notification.core.usecase.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.notification.core.usecase.MigrationUseCase;

@RequiredArgsConstructor
@Slf4j
@Service
public class MigrationUseCaseImpl implements MigrationUseCase {
    @Override
    public void migrationCloseTD() {
        log.info("========== SUMMARY ==========");
    }
}
