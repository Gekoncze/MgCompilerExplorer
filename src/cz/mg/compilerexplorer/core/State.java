package cz.mg.compilerexplorer.core;

import cz.mg.collections.Collection;


public class State {
    private final Collection<Node> parts;
    private final Collection<Node> infos;
    private final Collection<Node> links;

    public State(Collection<Node> parts, Collection<Node> infos, Collection<Node> links) {
        this.parts = parts;
        this.infos = infos;
        this.links = links;
    }

    public Collection<Node> getParts() {
        return parts;
    }

    public Collection<Node> getInfos() {
        return infos;
    }

    public Collection<Node> getLinks() {
        return links;
    }
}
