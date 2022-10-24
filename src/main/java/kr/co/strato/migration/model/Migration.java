package kr.co.strato.migration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Migration implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String migrationId;
	private String status;
	private String resultVal;
	private String labelKey;
	private String labelVal;
	private String clustNm;
	private String nodeNm;
	private String tgClustNm;
	private String tgNodeNm;
	private String reqDtti;
	private String endDtti;
	private String namespace;

	private String endpoint;

	private  String api;

	private String apiToken;
	private String backupNm;
	private String restoreNm;
	public enum STATUS {
		REQUEST, // 작업요청
		CHECK, // 확인
		BACKUP, // 백업
		RESTORE, // 복구
		DELETE, // 삭제
		COMPLETE;// 완료
	}


	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void Endpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getApi(){
		return api;
	}

	public void Api(String api) {
		this.api = api;
	}

	public String getApiToken(){
		return apiToken;
	}

	public void ApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public String getMigrationId() {
		return migrationId;
	}

	public void setMigrationId(String migrationId) {
		this.migrationId = migrationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResultVal() {
		return resultVal;
	}

	public void setResultVal(String resultVal) {
		this.resultVal = resultVal;
	}


	public String getLabelKey() {
		return labelKey;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

	public String getLabelVal() {
		return labelVal;
	}

	public void setLabelVal(String labelVal) {
		this.labelVal = labelVal;
	}

	public String getClustNm() {
		return clustNm;
	}

	public void setClustNm(String clustNm) {
		this.clustNm = clustNm;
	}

	public String getNodeNm() {
		return nodeNm;
	}

	public void setNodeNm(String nodeNm) {
		this.nodeNm = nodeNm;
	}

	public String getTgClustNm() {
		return tgClustNm;
	}

	public void setTgClustNm(String tgClustNm) {
		this.tgClustNm = tgClustNm;
	}

	public String getTgNodeNm() {
		return tgNodeNm;
	}

	public void setTgNodeNm(String tgNodeNm) {
		this.tgNodeNm = tgNodeNm;
	}

	public String getReqDtti() {
		return reqDtti;
	}

	public void setReqDtti(String reqDtti) {
		this.reqDtti = reqDtti;
	}

	public String getEndDtti() {
		return endDtti;
	}

	public void setEndDtti(String endDtti) {
		this.endDtti = endDtti;
	}


	public String getBackupNm() {
		return backupNm;
	}

	public void setBackupNm(String backupNm) {
		this.backupNm = backupNm;
	}

	public String getRestoreNm() {
		return restoreNm;
	}

	public void setRestoreNm(String restoreNm) {
		this.restoreNm = restoreNm;
	}
}
