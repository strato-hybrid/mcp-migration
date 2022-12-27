package kr.co.strato.migration.controller;

import io.fabric8.kubernetes.api.model.PersistentVolumeList;
import io.kubernetes.client.openapi.models.V1PodList;
import io.swagger.annotations.ApiOperation;
import kr.co.strato.migration.model.Migration;
import kr.co.strato.migration.model.MigrationCreateResource;
import kr.co.strato.migration.model.MigrationResource;
import kr.co.strato.migration.service.MigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MigrationController {

    @Autowired
    private MigrationService migrationService;

    @RequestMapping(value = "/backup/list", method = RequestMethod.GET)
    @ResponseBody
    public List<MigrationResource> getBackupList(@RequestParam String endpoint, @RequestParam String apiToken) {
        Migration migration = Migration.builder()
                .apiToken(apiToken)
                .endpoint(endpoint)
                .build();
        List<MigrationResource> result = migrationService.getBackupList(migration);
        ///System.out.println("result : "+result.toString());
        return result;
    }





    @RequestMapping(value = "/pod/list", method = RequestMethod.GET)
    @ResponseBody
    public V1PodList getPodList(@RequestParam String namespace, @RequestParam String endpoint, @RequestParam String apiToken) {
        Migration migration = Migration.builder()
                .apiToken(apiToken)
                .endpoint(endpoint)
                .namespace(namespace)
                .build();
        V1PodList results = migrationService.getPodList(migration);
        return results;
    }



    @RequestMapping(value = "/pv/list", method = RequestMethod.GET)
    @ResponseBody
    public PersistentVolumeList getPersistentVolumeList(@RequestParam String endpoint, @RequestParam String apiToken) {
        Migration migration = Migration.builder()
                .apiToken(apiToken)
                .endpoint(endpoint)
                .build();
        PersistentVolumeList results = migrationService.getPersistentVolumeList(migration);
        return results;
    }

    @ApiOperation(value="백업 생성")
    @RequestMapping(value = "/backup/create", method = RequestMethod.POST)
    @ResponseBody
    public MigrationCreateResource createBackupNamespace(@RequestParam String endpoint, @RequestParam String apiToken, @RequestParam String backupNm, @RequestParam String namespace) {
        Migration migration = Migration.builder()
                .backupNm(backupNm)
                .namespace(namespace)
                .apiToken(apiToken)
                .endpoint(endpoint)
                .build();
        ResponseEntity<MigrationCreateResource> response = migrationService.createBackupNamespace(migration);
        ///System.out.println("result : "+result.toString());
        return response.getBody();
    }


}
