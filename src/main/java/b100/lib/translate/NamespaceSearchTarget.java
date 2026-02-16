package b100.lib.translate;

public class NamespaceSearchTarget implements SearchTarget {

	private String namespace;
	
	public NamespaceSearchTarget(String namespace) {
		if(namespace == null) {
			throw new NullPointerException("Namespace is null!");
		}
		
		this.namespace = namespace;
	}

	@Override
	public String getFullPath(String language) {
		return "/assets/" + namespace + "/lang/" + language + ".lang";
	}
	
	public String getNamespace() {
		return namespace;
	}
	
}
