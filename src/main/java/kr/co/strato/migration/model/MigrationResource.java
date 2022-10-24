package kr.co.strato.migration.model;

import java.util.List;

public class MigrationResource {
	private final String apiVersion = "velero.io/v1";
	private String kind;

	private List<Items> items;

	public enum Kind {
		Backup, Restore
	}
	public static class Items{
		private String kind;
		private Metadata metadata;

		private Spec spec;

		public String getKind() {
			return kind;
		}
		public void setKind(String kind) {
			this.kind = kind;
		}
		public Metadata getMetadata() {
			return metadata;
		}
		public void setMetadata(Metadata metadata) {
			this.metadata = metadata;
		}


		public static class Metadata{
			private String name;
			private String namespace;
			private String resourceVersion;
			private String uid;

			private String creationTimestamp;
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getNamespace() {
				return namespace;
			}
			public void setNamespace(String namespace) {
				this.namespace = namespace;
			}
			public String getResourceVersion() {
				return resourceVersion;
			}
			public void setResourceVersion(String resourceVersion) {
				this.resourceVersion = resourceVersion;
			}
			public String getUid() {
				return uid;
			}
			public void setUid(String uid) {
				this.uid = uid;
			}
			public String getCreationTimestamp() {
				return creationTimestamp;
			}
			public void setCreationTimestamp(String creationTimestamp) {
				this.creationTimestamp = creationTimestamp;
			}
		}
		public Spec getSpec() {
			return spec;
		}
		public void setSpec(Spec spec) {
			this.spec = spec;
		}

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

	public List<Items> getItems() {
		return items;
	}
	public void setItems(List<Items> items) {
		this.items = items;
	}

	
}
