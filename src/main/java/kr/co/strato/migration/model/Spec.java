package kr.co.strato.migration.model;

import java.util.List;
public class Spec {
	private final String ttl = "24h0m0s"; // Backup 파일 유지 시간
	private final String storageLocation = "default";
	private List<String> includedNamespaces;
	private List<String> includedResources;
	private LabelSelector labelSelector;
	private String backupName;
    	
	public static class LabelSelector{
		private MatchLabels matchLabels;
		
		public MatchLabels getMatchLabels() {
			return matchLabels;
		}

		public void setMatchLabels(MatchLabels matchLabels) {
			this.matchLabels = matchLabels;
		}

		public static class MatchLabels{
			private String app;

			public String getApp() {
				return app;
			}

			public void setApp(String app) {
				this.app = app;
			}
		}
	}

	public List<String> getIncludedNamespaces() {
		return includedNamespaces;
	}

	public void setIncludedNamespaces(List<String> includedNamespaces) {
		this.includedNamespaces = includedNamespaces;
	}

	public List<String> getIncludedResources() {
		return includedResources;
	}

	public void setIncludedResources(List<String> includedResources) {
		this.includedResources = includedResources;
	}

	public LabelSelector getLabelSelector() {
		return labelSelector;
	}

	public void setLabelSelector(LabelSelector labelSelector) {
		this.labelSelector = labelSelector;
	}

	public String getTtl() {
		return ttl;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public String getBackupName() {
		return backupName;
	}

	public void setBackupName(String backupName) {
		this.backupName = backupName;
	}
	
}
