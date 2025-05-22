package vn.com.notification.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.notification.common.api.ResponseApi;
import vn.com.notification.core.usecase.MigrationUseCase;

@RestController
@RequestMapping("/v1/internal/migration")
// @RequiredArgsConstructor
@Slf4j
@ConditionalOnExpression("${application.migration-app-notification-enable}")
public class MigrationInternalController {
    private final TaskExecutor taskExecutor;
    private final MigrationUseCase migrationUseCase;
    public MigrationInternalController(TaskExecutor taskExecutor, MigrationUseCase migrationUseCase){
        this.taskExecutor = taskExecutor;
        this.migrationUseCase = migrationUseCase;
    }
    @PostMapping
    public ResponseApi<Void> migrateCloseTD() {
        taskExecutor.execute(migrationUseCase::migrationNotificationTemplate);
        return ResponseApi.success(null);
    }
}
