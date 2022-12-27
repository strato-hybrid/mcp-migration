package kr.co.strato.migration.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimList;
import io.fabric8.kubernetes.api.model.PersistentVolumeList;
import io.kubernetes.client.openapi.models.V1PodList;
import kr.co.strato.migration.config.RestTemplateGenerator;
import kr.co.strato.migration.model.Migration;
import kr.co.strato.migration.model.MigrationCreateResource;
import kr.co.strato.migration.model.MigrationResource;
import kr.co.strato.migration.model.Spec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MigrationService {
    private static final Logger logger = LoggerFactory.getLogger(MigrationService.class);


    private static final String POD_LIST_URL = "/api/v1/namespaces/{namespace}/pods";
    private static final String PERSISTENT_VOLUME_CLAIM_URL = "/api/v1/namespaces/{namespace}/persistentvolumeclaims";
    private static final String PERSISTENT_VOLUME_URL = "/api/v1/persistentvolumes";
    private static final String BACKUP_LIST_URL = "/apis/velero.io/v1/backups";
    private static final String BACKUP_CREATE_URL = "/apis/velero.io/v1/namespaces/velero/backups";



    public List<MigrationResource> getBackupList(Migration migration){
        String apiUrl = BACKUP_LIST_URL;
        migration.Api(apiUrl);

        ResponseEntity<JsonNode> response = null;
        List<MigrationResource> result = null;
        try {
            response = execute(HttpMethod.GET,migration);
            logger.info("{}: {}","result.getBody",response.getBody());

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

           result = mapper.convertValue(response.getBody(), new TypeReference<List<MigrationResource>>(){});

        } catch (Exception e) {
            logger.error(e.toString());
        }

        logger.info("{}: {}","result",result);
        return result;
    }


    public V1PodList getPodList(Migration migration) {
        String apiUrl = POD_LIST_URL;

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("namespace", migration.getNamespace());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(apiUrl);
        apiUrl = uriBuilder.buildAndExpand(uriVariables).toUriString();

        migration.Api(apiUrl);
        V1PodList result = new V1PodList();
        try {
            ResponseEntity<V1PodList> response = execute(HttpMethod.GET, migration);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            mapper.registerModule(new JavaTimeModule());
            result = mapper.convertValue(response.getBody(), V1PodList.class);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public PersistentVolumeList getPersistentVolumeList(Migration migration) {
        String apiUrl = PERSISTENT_VOLUME_URL;

        migration.Api(apiUrl);
        PersistentVolumeList result = new PersistentVolumeList();
        try {
            ResponseEntity<PersistentVolumeList> response = execute(HttpMethod.GET, migration);
            System.out.println(response.getBody());

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            mapper.registerModule(new JavaTimeModule());
            result = mapper.convertValue(response.getBody(), PersistentVolumeList.class);

            System.out.println(result);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return result;
    }




    public ResponseEntity execute(HttpMethod httpMethod, Migration migration) {

        String url = migration.getEndpoint() + migration.getApi();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(migration.getApiToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity response = null;
        try {
            RestTemplate restTemplate = RestTemplateGenerator.getRestTemplate();
            response = restTemplate.exchange(url, httpMethod, httpEntity, Map.class);
        } catch (NoSuchAlgorithmException e) {
            logger.info("NoSuchAlgorithmException "+e.toString());
            e.printStackTrace();
        } catch (KeyStoreException e) {
            logger.info("KeyStoreException "+e.toString());
            e.printStackTrace();
        } catch (KeyManagementException e) {
            logger.info("KeyManagementException "+e.toString());
            e.printStackTrace();
        }
        //restTemplate.exchange(url, httpMethod, httpEntity, Map.class);
       // System.out.println(response.getBody());
        return response;

    }

    public ResponseEntity<MigrationCreateResource> createBackupNamespace(Migration migration){
        String apiUrl = BACKUP_CREATE_URL;

        migration.Api(apiUrl);

        MigrationCreateResource createResource = new MigrationCreateResource();
        MigrationCreateResource.Metadata metadata = new MigrationCreateResource.Metadata();
        createResource.setKind("Backup");
        metadata.setName(migration.getBackupNm());

        Spec spec = new Spec();
        List<String> includeNamespace = new ArrayList<String>();
        //includeNamespace.add("*");
        includeNamespace.add(migration.getNamespace());
        spec.setIncludedNamespaces(includeNamespace);

        createResource.setSpec(spec);
        createResource.setMetadata(metadata);


        ObjectMapper mapper = new ObjectMapper();
        MigrationCreateResource requestBody = mapper.convertValue(createResource, MigrationCreateResource.class);

        ResponseEntity<MigrationCreateResource> response = postNamespace(HttpMethod.POST, migration,requestBody);
        logger.info("{}: {}","result",response.getBody());
        logger.info("{}: {}", "backupNamespace", "OK");
        return response;
    }

    public ResponseEntity<MigrationCreateResource> postNamespace(HttpMethod httpMethod, Migration migration,MigrationCreateResource requestBody){
        String uri = migration.getEndpoint()+migration.getApi();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(migration.getApiToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MigrationCreateResource> httpEntity = new HttpEntity<>(requestBody, headers);

        //   HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = null;
        try {
            restTemplate = RestTemplateGenerator.getRestTemplate();

        } catch (NoSuchAlgorithmException e) {
            logger.info("NoSuchAlgorithmException "+e.toString());
            e.printStackTrace();
        } catch (KeyStoreException e) {
            logger.info("KeyStoreException "+e.toString());
            e.printStackTrace();
        } catch (KeyManagementException e) {
            logger.info("KeyManagementException "+e.toString());
            e.printStackTrace();
        }

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, MigrationCreateResource.class);
    }
}
