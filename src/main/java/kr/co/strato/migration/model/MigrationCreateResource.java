package kr.co.strato.migration.model;

public class MigrationCreateResource {
	private final String apiVersion = "velero.io/v1";
	private String kind;


	public enum Kind {
		Backup, Restore
	}
	private Metadata metadata;

	private Spec spec;
	public static class Metadata{
		private String name;
		private final String namespace = "velero";

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNamespace() {
			return namespace;
		}


	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public Spec getSpec() {
		return spec;
	}
	public void setSpec(Spec spec) {
		this.spec = spec;
	}

	public String getApiVersion() {
		return apiVersion;
	}
	
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	
}
